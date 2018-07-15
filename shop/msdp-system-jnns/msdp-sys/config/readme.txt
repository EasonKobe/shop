1、config 作用
      利用profiles 实现多环境打包，为自动化部署提供准备
      
2、示例
   a、打包mysql环境项目
      clean install -P dev_mysql
      
   b、打包oracle环境项目
      clean install -P dev_oracle
注: 默认打包为oracle环境， 可更改  scf-sys/pom.xml 的 profiles标签的activation子标签  来实现默认打包环境