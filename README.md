# MyButterKnife
用编译时注解实现的,类ButterKnife的控件绑定框架.


本项目通过自定义@BindView以及@OnClick注解,并通过apt在编译期间动态生成类,实现ButterKnife的控件绑定及点击事件绑定效果.
除此之外,也尝试了自定义运行时注解,通过反射在运行时处理注解.这也是ButterKnife类库在7.0以前的版本采用的方式.

本项目旨在学习和理解运行时注解及apt的使用,并浅尝辄止的探究了ButterKnife类库的原理.
参考文章:https://blog.csdn.net/binbinqq86/article/details/79610980
