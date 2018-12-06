import os
import keras
import skimage
import numpy as np

base_path = "/hdd/yejiandong/datasets/overlap-0.25-dataset/%s_%s"
dtypes = ['wsi', 'sobel', 'gaussian']


class MyDataset(keras.utils.Sequence):
    def __init__(self, data_path, mode='train', batch_size=32):
        self.x = []
        self.y = []
        self.batch_size = batch_size
        self.mode = mode
        with open(data_path, 'r') as f:
            for line in f:
                xx, yy = line.strip().split(',')
                self.x.append(xx)
                self.y.append(int(yy))

        self.size = len(self.y)

    def __getitem__(self, index):
        begin_idx = index*self.batch_size
        end_idx = (index+1)*self.batch_size
        img_names = self.x[begin_idx:end_idx]
        labels = self.y[begin_idx:end_idx]

        wsi_imgs = []
        gaussian_imgs = []
        sobel_imgs = []
        for img_name in img_names:
            a, b, c = self.__get_single_image(img_name)
            wsi_imgs.append(a)
            gaussian_imgs.append(b)
            sobel_imgs.append(c)

        return [np.array(wsi_imgs), np.array(gaussian_imgs), np.array(sobel_imgs)], np.array(labels)

    def __get_single_image(self, img_name):
        wsi_img_path = os.path.join(base_path % ('wsi', self.mode), img_name)
        gaussian_img_path = os.path.join(
            base_path % ('gaussian', self.mode), img_name)
        sobel_img_path = os.path.join(
            base_path % ('sobel', self.mode), img_name)

        wsi_img = skimage.io.imread(wsi_img_path, dtypes=np.float32)/255
        wsi_img = (wsi_img-np.array([0.64639061, 0.56044774, 0.61909978])
                   )/np.array([0.1491973, 0.17535066, 0.12751725])
        wsi_img = np.float32(wsi_img)

        gaussian_img = skimage.io.imread(
            gaussian_img_path, dtypes=np.float32)/255
        gaussian_img = (
            gaussian_img-np.array([0.5761667257809361, ]))/np.array([0.16491364571692993, ])
        gaussian_img = np.float32(gaussian_img)
        gaussian_img = np.expand_dims(gaussian_img, axis=-1)

        sobel_img = skimage.io.imread(sobel_img_path, dtypes=np.float32)/255
        sobel_img = (
            sobel_img-np.array([0.07810377783926128, ]))/np.array([0.06767134977996857, ])
        sobel_img = np.float32(sobel_img)
        sobel_img = np.expand_dims(sobel_img, axis=-1)

        return wsi_img, gaussian_img, sobel_img

    def __len__(self):
        # return int(len(self.x))
        return int(np.ceil(len(self.x) / float(self.batch_size)))
