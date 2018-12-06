import keras
from keras.models import Model
from keras.datasets import cifar10
from keras.utils.vis_utils import plot_model
from keras.applications import ResNet50


def residual_block(x, filter, kernel_size, stride=1):
    y = keras.layers.Conv2D(
        filters=filter, kernel_size=kernel_size, strides=stride, padding='SAME', use_bias=False, init='lecun_uniform')(x)
    y = keras.layers.BatchNormalization()(y)
    y = keras.layers.Activation('selu')(y)
    y = keras.layers.Conv2D(
        filters=filter, kernel_size=kernel_size, strides=1, padding="SAME", use_bias=False, init='lecun_uniform')(y)
    y = keras.layers.BatchNormalization()(y)

    if stride != 1 or filter != x.shape[3]:
        x = keras.layers.Conv2D(filter, 1, strides=stride,
                                padding='SAME', use_bias=False, init='lecun_uniform')(x)
        x = keras.layers.BatchNormalization()(x)

    y = keras.layers.Add()([y, x])
    y = keras.layers.Activation('selu')(y)
    return y


def residual_block_v2(x, filter, kernel_size, stride=2, data_format='channels_first'):
    y = keras.layers.BatchNormalization()(x)
    y = keras.layers.Activation('selu')(y)
    y = keras.layers.Conv2D(filter, kernel_size=kernel_size, strides=stride,
                            padding="SAME", use_bias=False, kernel_initializer='lecun_uniform')(y)
    y = keras.layers.BatchNormalization()(y)
    y = keras.layers.Activation('selu')(y)
    y = keras.layers.Conv2D(filter, kernel_size=kernel_size, strides=1,
                            padding="SAME", use_bias=False, kernel_initializer='lecun_uniform')(y)

    if stride != 1 or filter != x.shape[3]:
        x = keras.layers.Conv2D(filter, 1, strides=stride,
                                padding='SAME', use_bias=False, init='lecun_uniform')(x)
        x = keras.layers.BatchNormalization()(x)

    y = keras.layers.Add()([y, x])
    return y


def ResNet(inputs, blocks, num_class):
    # 特殊的输入---------------
    wsi = keras.layers.Conv2D(filters=3, kernel_size=3, strides=1,
                              padding="SAME", use_bias=False, kernel_initializer='lecun_uniform')(inputs[0])
    x = keras.layers.Concatenate(axis=-1)([wsi, inputs[1], inputs[2]])
    # 特殊的输入---------------

    x = keras.layers.Conv2D(filters=64, kernel_size=7,
                            strides=2, padding='SAME', use_bias=False, kernel_initializer='lecun_uniform')(x)
    x = keras.layers.BatchNormalization()(x)

    for block in blocks:
        x = residual_block(x, *block)

    x = keras.layers.GlobalAveragePooling2D()(x)
    x = keras.layers.Dense(num_class, activation='softmax')(x)

    model = Model(inputs=inputs, outputs=x)
    return model


def make_resnet18(inputs, num_class=10):
    blocks = [[64, 3, 2], [64, 3, 1],
              [128, 3, 2], [128, 3, 1],
              [256, 3, 2], [256, 3, 1],
              [512, 3, 2], [512, 3, 1]]
    return ResNet(inputs, blocks, num_class)


def make_cifar10_resnet18(inputs, num_class=10):
    blocks = [[16, 3, 1], [16, 3, 1], [16, 3, 1],
              [32, 3, 2], [32, 3, 1], [32, 3, 1],
              [64, 3, 2], [64, 3, 1], [64, 3, 1]]

    return ResNet(inputs, blocks, num_class)


if __name__ == '__main__':

    import os
    import tensorflow as tf
    import keras.backend as K
    import numpy as np
    os.environ['CUDA_VISIBLE_DEVICES'] = '1'
    config = tf.ConfigProto()
    config.gpu_options.allow_growth = True
    sess = tf.Session(config=config)

    inputs = keras.layers.Input((32, 32, 3))
    model = make_resnet18(inputs)
    model.summary()

    optimizer = keras.optimizers.Adam(lr=0.001)
    model.compile(optimizer=optimizer,
                  loss='sparse_categorical_crossentropy', metrics=['acc'])
    plot_model(model, to_file="model.png", show_shapes=True)

    (x_train, y_train), (x_test, y_test) = cifar10.load_data()

    # x_train = x_train/255
    # x_test = x_test/255
    # x_train[:, :, :] -= [0.4914, 0.4822, 0.4465]
    # x_train[:, :, :] /= [0.2023, 0.1994, 0.2010]
    # x_test[:, :, :] -= [0.4914, 0.4822, 0.4465]
    # x_test[:, :, :] /= [0.2023, 0.1994, 0.2010]

    # model.fit(x_train, y_train, batch_size=256,
    #           validation_data=(x_test, y_test), epochs=40)

    data_gen = keras.preprocessing.image.ImageDataGenerator(
        featurewise_center=True,
        featurewise_std_normalization=True,
        rotation_range=20,
        width_shift_range=0.2,
        height_shift_range=0.2,
        horizontal_flip=True)

    data_gen.fit(x_train)

    test_data_gen = keras.preprocessing.image.ImageDataGenerator(
        featurewise_center=True,
        featurewise_std_normalization=True
    )
    test_data_gen.fit(x_train)

    callbacks = [
        keras.callbacks.EarlyStopping(patience=30),
        keras.callbacks.ReduceLROnPlateau(patience=10, min_lr=1e-7, verbose=1),
        keras.callbacks.TensorBoard(log_dir="./log/base", update_freq='batch'),
        keras.callbacks.ModelCheckpoint("./saved_model/base")
    ]

    model.fit_generator(data_gen.flow(x_train, y_train, batch_size=256),
                        validation_data=test_data_gen.flow(
                            x_test, y_test, batch_size=256),
                        validation_steps=len(x_test)/256,
                        steps_per_epoch=len(x_train) / 256, epochs=500, use_multiprocessing=True, callbacks=callbacks)
    rs = model.evaluate_generator(test_data_gen.flow(x_test, y_test, batch_size=256),
                                  steps=len(x_test)/256)
    print(rs)
