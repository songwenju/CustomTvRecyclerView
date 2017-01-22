###这是一个针对Android Tv Launcher页的recyclerView

####CustomTvRecyclerView 2.0
下面是效果图：
![image](https://github.com/songwenju/CustomTvRecyclerView/blob/master/raw/master/screenshots/tvRecycler2.0.gif)


该版本添加和修复的以下功能：
1.添加了垂直的RecyclerView，并实现了可以控制横向recyclerView的效果  
2.左右箭头点击后RecyclerView的条目不会获得焦点，解决了滑动冲突。  
3.横向RecyclerView5.0以下版本之后条目放上之后会出现被压盖的情况，这里修复了该bug。  
4.在RecyclerView内部不再提供类似于放大抬高z轴的操作，这里只提供了focus状态的接口，具体的逻辑在RecyclerView使用处提供回调。  

对应的博客地址：http://www.jianshu.com/p/f02bce783434
  

***
####CustomTvRecyclerView 1.0
下面是效果图：

![image](https://github.com/songwenju/CustomTvRecyclerView/blob/master/raw/master/screenshots/tvRecycler.gif)


该自定义的RecyclerView实现了一下一些功能:  
1.响应五向键，按下五向键的上下左右会跟着移动，并获得焦点，在获得焦点时会抬高   
2.在鼠标hover在条目上时会获得焦点。   
3.添加了条目的点击和长按事件。   
4.添加了是否第一个可见条目和是否是最后一个可见条目的方法。  
5.在item获得焦点时和失去焦点时，这里有相应的回调方法。   
项目对应的博客地址：http://www.jianshu.com/p/566bd6188f4d

