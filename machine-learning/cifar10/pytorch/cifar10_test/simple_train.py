from utils import train_model, History
from torch import nn, optim
from torchvision import transforms
from torchvision import datasets
from torch.utils.data import DataLoader
from torchvision.models import inception_v3
from torchsummary import summary

model = inception_v3(aux_logits=False)
inchannel = model.fc.in_features
print(inchannel)
model.fc = nn.Linear(inchannel, 5)
model = model.cuda()
summary(model, (3, 300, 300))

loss_fn = nn.CrossEntropyLoss(reduce=True)
optimizer_ft = optim.Adam(model.parameters(), lr=0.01)

transform = transforms.Compose(
    [transforms.Resize((300, 300)),
        transforms.ToTensor(),
        transforms.Normalize((0.64639061, 0.56044774, 0.61909978), (0.1491973, 0.17535066, 0.12751725))])

train_data = datasets.ImageFolder(
    "/hdd/yejiandong/datasets/overlap-0.25-dataset/wsi_train", transform=transform)
train_loader = DataLoader(train_data, batch_size=64,
                          shuffle=True, pin_memory=True, num_workers=10)

valid_data = datasets.ImageFolder(
    "/hdd/yejiandong/datasets/overlap-0.25-dataset/wsi_valid", transform=transform)
valid_loader = DataLoader(valid_data, batch_size=64,
                          shuffle=False, pin_memory=True, num_workers=10)

history = History(model, "inception", resume=False)
train_model(history, train_loader, valid_loader,
            loss_fn, optimizer_ft, num_epochs=30)
