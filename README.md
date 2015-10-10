# RecyclerViewDemo

### 概述

RecyclerView 好似说烂了，之前也使用过，好似也没有在正式开发中使用，现在想真正切换过来，想想将会使用到的功能，逐一整理出来。比如，下拉刷新，上拉加载，瀑布流等。

关于 RecyclerView 的基本信息不再介绍，这篇主要整理 RecyclerView 的基本使用，线性列表，网格布局，瀑布流，点击事件等。

### Dependencies

前提条件得满足是吧。

```
compile 'com.android.support:cardview-v7:23+'
compile 'com.android.support:recyclerview-v7:23+'

```
配合 CardView 感觉效果让我比较愉悦。RecyclerView 好似没有像 ListView 那样的 item 分割线，所以 CardView 的阴影层级能够很好的把 item 间隔。

[参看全文及效果](http://llzz.me/2015/10/08/RecyclerView-1/)