import warnings
import torch
import torch.nn as nn
import torch.nn.functional as F
import torch.optim as optim
import pickle

from torch.autograd import Variable
import torchvision
from torchvision import datasets, models, transforms
import time
import os
import numpy as np
from torch.utils.data import WeightedRandomSampler
import matplotlib.pyplot as plt
from tqdm import tqdm
import seaborn as sns
sns.set()

warnings.filterwarnings("ignore", category=DeprecationWarning)
warnings.filterwarnings("ignore", category=UserWarning)


class History:
    def __init__(self, model, model_name, resume=True):
        self.model_name = model_name
        self.save_path = os.path.join("./saved_models", model_name)
        self.epoch = 0
        self.global_step = -1
        self.train_steps = []
        self.train_loss = []
        self.train_acc = []
        self.valid_steps = []
        self.valid_loss = []
        self.valid_acc = []
        self.batch_steps = []
        self.batch_loss = []
        self.model = model
        self.best_loss = 100000
        self.best_acc = 100000
        if model:
            self.best_state_dict = model.state_dict
        self.lr_history = []
        self.lr_steps = []

        self.check_and_create_dir(self.save_path)
        if resume:
            self.check_and_load()

    def check_and_create_dir(self, path):
        if os.path.exists(path):
            return None
        else:
            self.check_and_create_dir(os.path.dirname(path))
            os.mkdir(path)
            return None

    def save(self):
        save_path = os.path.join(self.save_path, "history.h5")
        data = {
            "epoch": self.epoch,
            "global_step": self.global_step,
            "train_steps": self.train_steps,
            "train_loss": self.train_loss,
            "train_acc": self.train_acc,
            "valid_steps": self.valid_steps,
            "valid_loss": self.valid_loss,
            "valid_acc": self.valid_acc,
            "batch_steps": self.batch_steps,
            "batch_loss": self.batch_loss,
            "best_loss": self.best_loss,
            "best_acc": self.best_acc,
            "best_state_dict": self.best_state_dict,
            "state_dict": self.model.state_dict(),
            'lr_history': self.lr_history,
            'lr_steps': self.lr_steps
        }
        torch.save(data, save_path)
        file_size = os.path.getsize(save_path)/1024/1024
        print("save history checkpoint, %.2d MB" % (file_size))

    def check_and_load(self):
        save_path = os.path.join(self.save_path, "history.h5")
        if os.path.exists(save_path):
            print("found a history checkpoint,loading...")
            data = torch.load(save_path)

            self.epoch = data['epoch']
            self.global_step = data['global_step']
            self.train_steps = data['train_steps']
            self.train_loss = data['train_loss']
            self.train_acc = data['train_acc']
            self.valid_steps = data['valid_steps']
            self.valid_loss = data['valid_loss']
            self.valid_acc = data['valid_acc']
            self.batch_steps = data['batch_steps']
            self.batch_loss = data['batch_loss']
            self.best_loss = data['best_loss']
            self.best_acc = data['best_acc']
            self.lr_history = data['lr_history']
            self.lr_steps = data['lr_steps']

            if self.model != None:
                self.best_state_dict = data['best_state_dict']
                self.model.load_state_dict(data['state_dict'])
            print("load finish. Epoch %d" % (self.epoch))
        else:
            print("can't not find history checkpoint in %s" % save_path)

    def step(self):
        self.global_step += 1

    def log(self, loss, acc=None, phase='train'):
        if phase == 'train':
            self.batch_steps.append(self.global_step)
            self.batch_loss.append(loss)
        elif phase == 'train_val':
            self.train_steps.append(self.global_step)
            self.train_loss.append(loss)
            self.train_acc.append(acc)
        elif phase == 'valid_val':
            self.valid_steps.append(self.global_step)
            self.valid_loss.append(loss)
            self.valid_acc.append(acc)

            # 保存最优的模型
            if acc < self.best_acc:
                self.best_loss = loss
                self.best_acc = acc
                self.best_state_dict = self.model.state_dict

            self.save()

    def log_lr(self, lr):
        self.lr_history.append(lr)
        self.lr_steps.append(self.global_step)

    def get_best_model(self):
        self.model.load_state_dict(self.best_state_dict)
        return self.model

    def plot_loss(self):
        plt.plot(self.batch_steps, self.batch_loss,
                 sns.xkcd_rgb["medium grey"], lw=0.5)
        plt.plot(self.train_steps, self.train_loss, '-',
                 self.valid_steps, self.valid_loss, '-')
        plt.legend(['batch_loss', 'train_loss', 'test_loss'])
        plt.title("loss curve")
        plt.xlabel("step")
        plt.ylabel("loss")
        plt.ylim([-1, 3])

    def plot_acc(self):
        plt.plot(self.train_steps, self.train_acc, '-',
                 self.valid_steps, self.valid_acc, '-')
        plt.legend(['train_acc', 'test_acc'])
        plt.title("acc curve")
        plt.xlabel("step")
        plt.ylabel("acc")

    def plot_lr(self):
        plt.plot(self.lr_steps, self.lr_history, 'o-')
        plt.legend(['lr'])
        plt.title("lr curve")
        plt.xlabel("step")
        plt.ylabel("lr")

    def plot(self):
        plt.figure(figsize=(12, 4), dpi=200)
        plt.subplot(1, 2, 1)
        self.plot_loss()
        plt.subplot(1, 2, 2)
        self.plot_acc()


def compareHistory(model1, model2):
    history1 = History(None, model1, resume=True)
    history2 = History(None, model2, resume=True)

    plt.figure(figsize=(12, 4), dpi=200)
    plt.subplot(1, 2, 1)
    plt.plot(history1.valid_steps, history1.valid_loss,
             history2.valid_steps, history2.valid_loss)
    plt.legend([model1+'_loss', model2+'_loss'])
    plt.title("loss")
    plt.xlabel("step")
    plt.ylabel("loss")

    plt.subplot(1, 2, 2)
    plt.plot(history1.valid_steps, history1.valid_acc,
             history2.valid_steps, history2.valid_acc)
    plt.legend([model1+'_acc', model2+'_acc'])
    plt.title("acc")
    plt.xlabel("step")
    plt.ylabel("loss")

    plt.twinx()
    plt.plot(history1.lr_steps, history1.lr_history, 'x-',
             history2.lr_steps, history2.lr_history, "x-")
    plt.legend([model1+"_lr", model2+'_lr'])
    plt.ylabel("lr")

    return history1, history2

def adjust_learning_rate(optimizer, epoch, init_lr=0.01):
    """Sets the learning rate to the initial LR decayed by 10 every 30 epochs"""
    lr = init_lr * (0.95**epoch)
    for param_group in optimizer.param_groups:
        param_group['lr'] = lr

def train_model(history,
                train_dataloader, valid_dataloader,
                loss_fn, optimizer, num_epochs=25, use_gpu=True, phases=['train', 'train_val', 'valid_val'], device='cuda:0'):
    since = time.time()

    model = history.model

    final_epochs = history.epoch+num_epochs
    epoch_loss = history.best_loss
    # scheduler = optim.lr_scheduler.ReduceLROnPlateau(
    #     optimizer, 'min', patience=10, verbose=True)

    for epoch in range(history.epoch, final_epochs):
        print('-' * 15)
        print('= Epoch {}/{} '.format(epoch, final_epochs - 1))
        print('-' * 15)

        # Each epoch has a training and validation phase
        for phase in phases:
            if phase == 'train':
                adjust_learning_rate(optimizer,epoch)
                history.log_lr(optimizer.param_groups[0]['lr'])
                model.train()  # Set model to training mode
            else:
                # Set model to evaluate mode
                model.eval()

            running_loss = []
            running_corrects = []
            running_count = 0

            # Iterate over data.
            dataloader = train_dataloader if phase.find(
                'train') >= 0 else valid_dataloader

            pbar = tqdm(dataloader, total=len(dataloader),leave=False)
            start_time = time.time()
            for imgs, labels in pbar:
                if use_gpu:
                    imgs = imgs.to(device, non_blocking=True)
                    labels = labels.to(device, non_blocking=True)

                # print(np.bincount(labels.numpy()))
                if phase == 'train':
                    history.step()

                # zero the parameter gradients
                optimizer.zero_grad()
                # forward
                outputs = model(imgs)
                preds = torch.argmax(outputs, -1)
                acc = int(labels.eq(preds).sum())
                count = int(len(labels))
                loss = loss_fn(outputs, labels)

                # backward + optimize only if in training phase
                if phase == 'train':
                    loss.backward()
                    optimizer.step()

                # statistics
                running_loss.append(float(loss.data[0]))
                running_corrects.append(acc)
                running_count += len(labels)

                history.log(float(loss.data[0]))
                
                pbar.set_description(
                    "[loss] %.4f [acc] %.4f" % (float(loss), acc/count))

                del imgs, outputs, labels, loss, preds

            epoch_loss = np.mean(running_loss)
            epoch_acc = float(np.sum(running_corrects)) / running_count

            time_elapsed = time.time() - start_time
            print('{} Loss: {:.4f} Acc: {:.4f} lr: {:.4f} time: {:.0f}m {:.0f}s'.format(
                phase, epoch_loss, epoch_acc, optimizer.param_groups[0]['lr'], time_elapsed // 60,
                 time_elapsed % 60))

            history.log(float(epoch_loss), float(epoch_acc), phase)
            torch.cuda.empty_cache()

        history.epoch += 1

    time_elapsed = time.time() - since
    print('Training complete in {:.0f}m {:.0f}s'.format(
        time_elapsed // 60, time_elapsed % 60))
    print('Best val Acc: {:4f}'.format(history.best_acc))
    # load best model weights
    # model.load_state_dict(best_model_wts)
    return history


def predict_model(model, dataloader):
    res = []
    for inputs, labels in dataloader:
        inputs = Variable(inputs.cuda())
        y = model(inputs)
        _, preds = torch.max(y.data, 1)
        res.extend(list(preds.cpu().numpy()))
    return res


def eval_model(model, dataloader):
    res = []
    gt = []
    for inputs, labels in dataloader:
        inputs = Variable(inputs.cuda())
        y = model(inputs)
        _, preds = torch.max(y.data, 1)
        res.extend(list(preds.cpu().numpy()))
        gt.extend(list(labels.numpy()))
    return res, gt


def get_dataloader(data_path, batch_size=64, sample=False, transform=None):
    data = torchvision.datasets.ImageFolder(
        root=data_path, transform=transform)

    if sample:
        target = [x[1] for x in data.imgs]
        class_sample_count = np.array(
            [len(np.where(target == t)[0]) for t in np.unique(target)])
        weight = 1. / class_sample_count
        samples_weight = np.array([weight[t] for t in target])

        samples_weight = torch.from_numpy(samples_weight)
        samples_weight = samples_weight.double()
        sampler = WeightedRandomSampler(samples_weight, len(samples_weight))

        dataloader = torch.utils.data.DataLoader(
            data, batch_size=64, shuffle=False, num_workers=2, sampler=sampler, pin_memory=True)
    else:
        dataloader = torch.utils.data.DataLoader(
            data, batch_size=64, shuffle=True, num_workers=2, pin_memory=True)

    return dataloader
