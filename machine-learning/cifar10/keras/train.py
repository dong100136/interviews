import warnings
from ResNet import make_resnet18
from DataLoader import MyDataset
import keras
from tqdm import tqdm
import numpy as np

import os
import tensorflow as tf
import keras.backend as K
os.environ['CUDA_VISIBLE_DEVICES'] = '0'
config = tf.ConfigProto()
config.gpu_options.allow_growth = True
sess = tf.Session(config=config)

warnings.filterwarnings("ignore", category=DeprecationWarning)
warnings.filterwarnings("ignore", category=UserWarning)


inputs = [keras.layers.Input((224, 224, 3)),
          keras.layers.Input((224, 224, 1)),
          keras.layers.Input((224, 224, 1))]

model = make_resnet18(inputs, num_class=5)

optimizer = keras.optimizers.Adam(lr=0.001)
model.compile(optimizer=optimizer,
              loss='sparse_categorical_crossentropy', metrics=['acc'])
# model.summary()

train_dataset = MyDataset(
    "/hdd/yejiandong/datasets/overlap-0.25-dataset/list.train", mode='train', batch_size=20)
valid_dataset = MyDataset(
    "/hdd/yejiandong/datasets/overlap-0.25-dataset/list.valid", mode='valid', batch_size=20)

print(len(train_dataset))

callbacks = [
    keras.callbacks.EarlyStopping(patience=30),
    keras.callbacks.ReduceLROnPlateau(patience=10, min_lr=1e-7, verbose=1),
    keras.callbacks.TensorBoard(log_dir="./log/base", update_freq='batch'),
    # keras.callbacks.ModelCheckpoint("./saved_model/base.h5")
]

model.fit_generator(train_dataset,
                    validation_data=valid_dataset,
                    workers=10,
                    epochs=500, use_multiprocessing=True, callbacks=callbacks)

# model.fit_generator(train_dataset,
#                     validation_data=valid_dataset,
#                     validation_steps=len(train_dataset.x),
#                     steps_per_epoch=len(valid_dataset.x),
#                     epochs=500, use_multiprocessing=True, callbacks=callbacks)

########################################################################################
# def run_model(model, dataset, mode='train'):
#     loss, acc = [], []
#     for (wsi, gaussian, sobel), labels in tqdm(dataset, total=len(dataset)):
#         if mode == 'train':
#             rs = model.train_on_batch([wsi, gaussian, sobel], labels)
#         elif mode == 'valid':
#             rs = model.test_on_batch([wsi, gaussian, sobel], labels)
#         loss.append(rs[0])
#         acc.append(rs[1])

#     print("%s mode loss %.5f, acc %.5f" % (mode, np.mean(loss), np.mean(acc)))
#     return np.mean(loss), np.mean(acc)


# best_loss = 100
# best_acc = 0
# best_epoch = 0
# for i in range(32):
#     print("------epoch %d-------" % i)
#     loss, acc = run_model(model, train_dataset, mode='train')
#     loss, acc = run_model(model, valid_dataset, mode='valid')

#     if acc > best_acc:
#         best_acc = acc
#         best_loss = loss
#         best_epoch = i

#     if i-best_epoch > 10:
#         new_lr = model.optimizer.lr*0.01
#         K.set_value(model.optimizer.lr, new_lr)
#         print("update lr to %f " % new_lr)
