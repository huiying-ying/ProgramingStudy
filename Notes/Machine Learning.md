# Machine Learning

## 1 基本概念

### 1.1 Sigmoid & ReLU  --神经元

Sigmoid 以相对柔和的方式对函数进行拟合，而 ReLU 则相对刚硬，呈现斜率突变的情况。

### 1.2 机器学习的通用步骤

###### 建立初始模型→定义误差函数→优化模型参数

<img src="imgs\Machine Learning\image-20220427203411300.png" alt="image-20220427203411300" style="zoom:50%;" />



### 1.3 学习模型调整

针对训练数据和测试数据误差结果不同的各个情况，对模型作出调整。

<img src="imgs\Machine Learning\image-20220427204138699.png" alt="image-20220427204138699" style="zoom:50%;" />



#### 1 训练数据的误差大

<img src="imgs\Machine Learning\image-20220426215630950.png" alt="image-20220426215630950" style="zoom: 50%;" />

##### Model Bias --模型过于简单/限制过于苛刻

**原因**：当前模型所能覆盖的种种可能，并不包括所要求解的最优解。

**解决方案**：通过加大模型的复杂度，能够实现训练数据误差值减小的趋势。

##### Optimization Issue --最优化方案无法找到最优解

**原因**：譬如梯度下降等求解方案，在遇到局部最优解或者达到迭代次数上限后，即会停止对全局最优解的寻找，造成即便最优解在寻找空间之中，仍然不能够求解得到的情况，另外**越复杂**的模型**越难**以找到最优解，因此会出现神经网络越复杂，测试数据误差越大的情况。

**解决方案**：采用合适的最优化方案(调整学习率、优化方案等)。

#### 2 训练数据误差小&测试数据误差大

##### Overfitting --过拟合

——在训练数据中呈现 L(θ) 损失减小的的趋势，而在测试数据中 L(θ) 损失反而增大的情况。

**解决途径**

###### 1. Less parameters, sharing parameters  

--减少参数的使用，譬如减小神经网络层数，减少每一层当中 sigmoid 的使用。

###### 2. Less features

--使用较少的特征值，即减少对数据的过分依赖。

###### 3. Early stopping

###### 4. Regularization

###### 5. Dropout

#### 3 合理利用训练数据

初级版本——

训练数据分为两半，一部分用于得到不同模型的误差结果，一部分用于对上述模型进行筛选

<img src="imgs\Machine Learning\image-20220427205916531.png" alt="image-20220427205916531" style="zoom:50%;" />

进阶版本——

训练数据等分为3份，每次选择不同的一份作为筛选所用（Val），另外两份用于训练数据（Train），分别测试不同的模型，每种模型再求误差平均，选择最小的一种作为更为合适的模型。

<img src="imgs\Machine Learning\image-20220427210219971.png" alt="image-20220427210219971" style="zoom:50%;" />

#### 4 优化方案分析

在梯度较小的**局部范围**，用误差函数的**泰勒展开式（二次为止）**进行拟合。

在梯度为0的参数点，一次项为0，仅根据梯度无法判断参数优化的步进方向，此时需要参考二次项。

<img src="imgs\Machine Learning\image-20220427210708746.png" alt="image-20220427210708746" style="zoom:50%;" />

通过分析**嗨森矩阵H**的不同情况，可以判断梯度为0的参考点究竟是最大值、最小值、还是**鞍点**。

##### Saddle point --鞍点

通过嗨森矩阵可以得到鞍点的下降方向，但实际问题中嗨森矩阵的计算量过大，往往选取其他方法进行求解。

<img src="imgs\Machine Learning\image-20220427211903547.png" alt="image-20220427211903547" style="zoom:50%;" />

## 2 卷积神经网络 （Convolutional Neural Networks, CNN）

### 2.1 全连接网络

--所需要的参数过多，对于图像处理而言，只需要观察图像的几个局部特征即可。

<img src="C:\Users\Sherry\AppData\Roaming\Typora\typora-user-images\image-20220510105328257.png" alt="image-20220510105328257" style="zoom: 50%;" />

### 2.2 基于全连接网络的简化

#### 2.2.1 第一种方式——Receptive field（感知域）



<img src="C:\Users\Sherry\AppData\Roaming\Typora\typora-user-images\image-20220510112117292.png" alt="image-20220510112117292" style="zoom:50%;" />

##### 常规设置的几个参数

###### Receptive field  --可以包含多个channel，形状大小都可以任意

###### kernel size --默认包含所有channel，3×3是常见的大小，7×7已经是非常大的尺寸

###### stride --通常设置为1，指的是每个Receptive field每次平移的距离

###### padding --补值，有多种方式

<img src="C:\Users\Sherry\AppData\Roaming\Typora\typora-user-images\image-20220510142633954.png" alt="image-20220510142633954" style="zoom: 33%;" />

#### 2.2.2 第二种方式——Parameter Sharing（共享参数）

<img src="imgs\Machine Learning\image-20220510143546615.png" alt="image-20220510143546615" style="zoom:33%;" />

neural的参数完全一样（但输出不会一样，因为输入不会一样）

##### 常用的参数

###### filter --指的是neural中相同的对应参数

<img src="imgs\Machine Learning\image-20220701141153672.png" alt="image-20220701141153672" style="zoom:67%;" />

<img src="imgs\Machine Learning\image-20220510143815754.png" alt="image-20220510143815754" style="zoom:33%;" />

#### 2.2.3 第三种方式——Max Pooling（在上述得到的Convolutional Layers的基础上）

经过filter处理的每一层数据，将其分为小组，不同的pooling有不同的机制，这里的Max Pooling就是选取其中最大的。

<img src="imgs\Machine Learning\image-20220510145131268.png" alt="image-20220510145131268" style="zoom:33%;" />

注：上述方法要灵活应用，譬如Alpha Go也作为图像进行处理，但pooling并不适用（因为抽去个别像素点后，整个棋局就变得不再一样）

往往 **convolution** 和 **pooling** 是配合使用的。

<img src="imgs\Machine Learning\image-20220701142131747.png" alt="image-20220701142131747" style="zoom:67%;" />

##### 经典CNN全步骤

<img src="imgs\Machine Learning\image-20220701143239254.png" alt="image-20220701143239254" style="zoom:67%;" />

## 3 Self-attention

**--面向输入向量的长度不再是确定的情况**

### 3.1 分类

①sequence中的每个向量都有一个label，即输入和输出的数目一样多，譬如词性识别 tagging

②每个sequence都只有一个label，譬如判断评价中表达的是积极的或者消极的情绪、通过音频判断说话者是谁

③每个sequence的输出不能够确定，譬如 translation 不同的语言对应的词条不一定是多少，需要机器自己判断

<img src="imgs\Machine Learning\image-20220510153216858.png" alt="image-20220510153216858" style="zoom:33%;" />

Self-attention 在 sequence labeling 中的作用是，考虑上下文分析一个词的词性，上下文不是固定长度的窗口，而是一个长短未知的句子。

### 3.2  实施步骤

左图所示为一种两种向量之间相关性 **α** 的计算方法，右边是另一种计算方法 **Dot-product** 的实施步骤。

（两个向量分别 × Wq 和 Wk 向量得到 q 和 k）

<img src="imgs\Machine Learning\image-20220510161420528.png" alt="image-20220510161420528" style="zoom: 50%;" />

- 先计算得到attention分数，即各个向量之间的相关性（步骤中的softmax可以用其他方法代替，Relu之类的）


<img src="imgs\Machine Learning\image-20220510162626640.png" alt="image-20220510162626640" style="zoom: 50%;" />

- 其中 **α** 表征了各个向量（包括自己）与自己的相关程度，根据这个attention 分数抽取重要的信息，即经过处理得到的向量 **v**，二者相乘后经过相加，得到最终的向量 **b**。

<img src="imgs\Machine Learning\image-20220510163007663.png" alt="image-20220510163007663" style="zoom:50%;" />

-  可将重复的步骤用矩阵相乘的方式得到

<img src="imgs\Machine Learning\image-20220701154019643.png" alt="image-20220701154019643" style="zoom:67%;" />

-  用矩阵表示整体过程

  <img src="imgs\Machine Learning\image-20220701154452369.png" alt="image-20220701154452369" style="zoom:67%;" />

### 3.3 Multi-head Self-attention

**head** 对应于不同种类的相关性，即不止一种 **q** ，对应的 **k** 和 **v** 也有相应的数量。

<img src="imgs\Machine Learning\image-20220701155102273.png" alt="image-20220701155102273" style="zoom:67%;" />

### 3.4 Positional Encoding 位置信息

![image-20220701155431241](imgs\Machine Learning\image-20220701155431241.png)

## 4 Seq2Seq——Transformer

Transformer 整体分为 Encoder 和 Decoder 两个部分，二者主要是中间部分有细微差异，也即二者的连接处，如图所示。

其中 Cross attention 是 Decoder 提供向量 q ，Encoder 提供向量 k 和 v 。

<img src="imgs\Machine Learning\image-20220702114536204.png" alt="image-20220702114536204" style="zoom:50%;" />

### 4.1 Encoder

这里以一篇论文为例，并不是这样的网络架构设计就是最好的。

<img src="imgs\Machine Learning\image-20220702105512724.png" alt="image-20220702105512724" style="zoom:33%;" />

将原文章的流程图进行拆解介绍

<img src="imgs\Machine Learning\image-20220702103251702.png" alt="image-20220702103251702" style="zoom: 25%;" />

其中 Transform 的每一个block之中有多个操作，即

1. 使用 residual ： 在 self-attention 的基础上加上本身

2. 对 residual 的结果进行 Layer normalizaion ：

   - Batch normalization 对 不同 **example** 不同的 **feature** 的同一个**dimension** 计算得到均值和方差；

   - Layer normalizaion 对 同一个**example** 同一个 **feature** 的不同 **dimension** 计算得到均值和方差。

3. 将 Norm 之后的结果输入到全连接层 FC ，之后经过同上的 residual 和 Layer normalizaion ，得到每个block的输出。





<img src="imgs\Machine Learning\image-20220702104719854.png" alt="image-20220702104719854" style="zoom:67%;" />

### 4.2 Decoder

<img src="imgs\Machine Learning\image-20220702110503990.png" alt="image-20220702110503990" style="zoom:50%;" />

其中 Decoder 中的 self-attention 改为 Masked self-attention ，其区别在于 Masked 中的每个输出向量不能与其后的输入向量建立关系。

<img src="imgs\Machine Learning\image-20220702110757120.png" alt="image-20220702110757120" style="zoom: 33%;" />

AT 与 NAT

<img src="imgs\Machine Learning\image-20220702111722276.png" alt="image-20220702111722276" style="zoom:50%;" />

## 6 自编码器（auto-encoder）

编码器将原始数据压缩成一个向量，而解码器则通过向量重构原数据，对于网络的训练目标是恢复出的数据越接近原数据越好。

<img src="imgs\Machine Learning\image-20220829150200432.png" alt="image-20220829150200432" style="zoom:50%;" />

更多**降维**的实现方法

<img src="imgs\Machine Learning\image-20220829151007801.png" alt="image-20220829151007801" style="zoom:33%;" />

de-noising encoder

![image-20220829151733160](C:\Users\Sherry\AppData\Roaming\Typora\typora-user-images\image-20220829151733160.png)

#### 几种分类

##### Feature Disentangle

![image-20220829154103074](imgs\Machine Learning\image-20220829154103074.png)

##### VQVAE

![image-20220829154013758](imgs\Machine Learning\image-20220829154013758.png)

文章摘要的输出：使得两个transformer合作，实现seq2seq2seq，为了使中间的embedding是人能够读得懂的句子，让其经过Discriminator的处理。

![image-20220829160632653](imgs\Machine Learning\image-20220829160632653.png)

用树结构表达一段文字

![image-20220829160524993](imgs\Machine Learning\image-20220829160524993.png)

#### 其他应用

##### Generator —— VAE(variational auto-encoder)

将 Embedding 进行解码的过程看做是 Generator 的一种应用。

![image-20220829163713132](imgs\Machine Learning\image-20220829163713132.png)

##### Compression

将 encoder 的输出当做是压缩的结果

![image-20220829164049987](imgs\Machine Learning\image-20220829164049987.png)

##### Anomaly Detection —— 异常检测

![image-20220829164321163](imgs\Machine Learning\image-20220829164321163.png)

以及相关文献。它与一般分类问题的区别在于，异常数据不够充分的前提下进行训练。

![image-20220829164632692](imgs\Machine Learning\image-20220829164632692.png)

其他异常检测的介绍

![image-20220829164934201](imgs\Machine Learning\image-20220829164934201.png)