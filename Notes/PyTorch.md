# PyTorch

## 1 加载数据

##### Dataset  - 负责如何获取数据

##### Dataloader  - 负责加载数据并为网络提供数据

### 1.1 Dataset

```python
from torch.util.data import Dataset
```

它继承抽象类 Dataset ，重写下述函数

__getitem__  提供方法获取数据

__len__  提供数据集的数量