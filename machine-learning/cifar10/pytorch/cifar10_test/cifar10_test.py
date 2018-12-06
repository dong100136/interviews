from utils import History, train_model
from ResNet import Cifar10_ResNet44, ResNet18
import torch
from torch import nn, optim
import torchvision
import torchvision.transforms as transforms
import os
from torchsummary import summary
os.environ['CUDA_VISIBLE_DEVICES'] = '0'

# transform = transforms.Compose(
#     [transforms.RandomSizedCrop(224),
#      transforms.RandomHorizontalFlip(),
#      transforms.ToTensor(),
#      transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5))])

transform = transforms.Compose(
    [transforms.ToTensor(),
        transforms.Normalize((0.5, 0.5, 0.5), (0.5, 0.5, 0.5))])

trainset = torchvision.datasets.CIFAR10(root='./data', train=True,
                                        download=True, transform=transform)
trainloader = torch.utils.data.DataLoader(trainset, batch_size=128,
                                          shuffle=True, num_workers=10, pin_memory=True)

testset = torchvision.datasets.CIFAR10(root='./data', train=False,
                                       download=True, transform=transform)
testloader = torch.utils.data.DataLoader(testset, batch_size=128,
                                         shuffle=False, num_workers=10, pin_memory=True)

classes = ('plane', 'car', 'bird', 'cat',
           'deer', 'dog', 'frog', 'horse', 'ship', 'truck')

model = Cifar10_ResNet44().cuda()

loss_fn = nn.CrossEntropyLoss(reduce=True)
optimizer_ft = optim.Adam(model.parameters(), lr=0.01)

history = History(model, "cifar10_transform", resume=True)
train_model(history, trainloader, testloader,
            loss_fn, optimizer_ft, num_epochs=30)
