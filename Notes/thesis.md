# The Technology of Constructing Network Space Fingerprint Database Based on Association Reasoning

## 面向关联推理的网络空间指纹库构建技术



### 1 Introduction



### 2 Related Work

#### 2.1 网络设备指纹构建

IP地址、MAC地址等标识符

GTID等基于时钟

基于流量

#### 2.2 关联规则挖掘



#### 2.3 关联分类算法典型应用



### 3 The Proposed Method

A. System Model and Assumptions



![image-20230113175707944](imgs/thesis/image-20230113175707944.png)

如图为无线网络空间的设备组成示意图，我们在所考察的空间里放置多个不同功能的传感器用于收集设备信息。各个传感器负责采集其探测有效范围内的一个或多个设备信息。每个传感器能够依据所采集数据形式对来源不同的数据加以区分。如利用麦克风阵列收集与设备配套的键盘音频时，能够通过音源角度区分数据来源，图中每个五边形链表示从同一音频原生数据处理得到的音频特征集，各个五边形分别表示不同的特征。其他传感器所获取的数据特征也一应以此方式表示。

数据的采集与处理在设备指纹构建的流程中是第一步也是极其重要的一步。但由于这并不是本篇文章研究的重点，因此假设我们已经从原生数据得到了易于处理的数据格式，即特征标签集。同样以音频数据为例，以{空心A}={a1, a2, ... , an}表示音频特征全集，它包含了所有能够从音频数据可分析得到的特征标签，该特征集的大小为n。A含于{空心A}表示某一设备可能表现出的特征。。类似的表示同样适用于加密流量、红外等其他类型的特征。由于一个设备会同时表现出不同类型的多种特征，设备某一时刻的特征集可以由F={A, P, I, ...}表示。

B. 构建关联规则库

在介绍了上述关于设备特征集的定义的前提下，我们着力探究设备特征之间，尤其是不同类型的特征之间的关系。基于经典Aprior算法，我们找出满足支持度设置下的频繁项集。之后我们借鉴文献【A design failure pre-alarming system using score- and vote-based associative classification】的方法，对关联规则进行排序。我们首先考虑关联规则所含特征的丰富性，即关注左侧特征数量（LHS），在此基础上置信度和支持度被广泛认为是两个最重要的度量，这里我们采用两者的几何平均值对其进行排序。在左侧特征数量和两个重要度量的几何平均值都相等的情况下，我们利用升力值打破规则之间的平衡。



**在代码实现部分，首先根据单个类型推断单个类型的，然后是两类推一类的，然后是两类推两类的，以次类推对推断规模进行排序。在上述每种推断规模下依据左侧特征数量、支持度与置信度的几何平均值、升力值进行排序。**

C. 组装特征

在获得网络空间中各个设备的多个类型特征后，组合同一设备的各个类型特征以及根据这些特征区分设备身份是将要解决的关键问题。

依据上一步骤中生成的关联规则，我们把目光放在不同类型的特征之间的规则上。在一开始，各个类型的特征之间相互独立，我们选取规则评分靠前的单类型特征与单类型特征之间的关联规则组装特征集。之后将组合后的多类型特征集作为整体，同样依据组合内特征和该组合中没有的其他类型特征的关联规则评分的排序，将该组合与其他单类型或组合好的多类型特征集进行组装，直至组合中包含了所有类型的特征。这里需要强调的一点是，空集作为各个类型的特征集的子集，同样代表了设备该类型的特征，即缺失该类型特征。但不同类型的特征集的空集显然代表了不同的意义。

组装后在答案库里能找到的视为组装成功，这里可以获得组装正确率。用ACS表示

我们关注设备特征组装的准确性。为了方便表示，我们用向量c=[f0,f1,f2...]表示某设备的特征集，其中fi为0或1，1表示含有该特征，0则表示不含有。向量的大小是所有特征全集尺寸的总和。相应的，我们将根据关联规则库组装的结果用向量f^表示，则特征的组装正确与否由两者是否相等决定。

D.预测分类

根据关联规则确定权重

预测失误的特征集中，除了组装正确但预测失误的例子，还有一些组装失败的例子，因此ACC仅仅表征了分类器的预测能力，实际的指纹构建准确率应当为ACS*ACC.



### 4 Simulation results

选酒水这个作为数据集

[SPMF: A Java Open-Source Data Mining Library (philippe-fournier-viger.com)](https://www.philippe-fournier-viger.com/spmf/index.php?link=datasets.php)

![image-20230126010546961](imgs/thesis/image-20230126010546961.png)

对比单一类型分类结果、多类型无权重分类结果、多类型按规则定权重分类结果

对比多类型无权重分类结果、多类型按规则定权重分类结果、正确结果三种情况下特征组装的准确性，分析特征组装与分类结果之间的联系





























![](imgs/thesis/image-20230104200442451.png)

#### 声音

Voiceprint recognition of multi scene network devices based on transfer learning

基于迁移学习的多场景网络设备声纹识别、

#### 多尺度

Dynamic Scale Detection Strategy for Network Target Based on Deep Learning

一种基于深度学习的网络目标动态尺度探测策略

#### 设备指纹

Fingerprint generation method based on multi feature weighted voting ensemble classifier for intrusion detection system

面向入侵检测系统的一种基于多特征加权投票集成分类器的指纹生成方法

#### 特征指纹

Extraction of Structured and Unstructured Features of Network Devices based on Triplet and Simhash

基于Simhash和Triplet的网络设备结构化及非结构化特征提取

