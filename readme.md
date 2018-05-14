
1. entity: 基础库，其他各个模块数据交换的基础类型；框架的基础类
2. flowWeb： web应用(基本就是提供controller，以后改名rest.)
3. persistent-neo4j: 数据持久层的neo4j实现
4. spring-myflow: 框架的spring实现
5. workflow: activiti工作流引擎，现在是单独服务。以后目标要集成到一起
6. form 动态表单services。从原来的flowWeb分离出来
7. flow-demo 模拟外部web工程的集成


    
要做的工作
-------------------------
1. 设计器集成
2. 流程的动态部署
3. Activiti的整合（长期）
4. 权限需要把组织结构加进去(先不考虑职务)
5. 表单部分
    1. 资源选择的提交，建立关系
    2. 明细数据（表格数据的全套）
    3. 其他组件（现在只有最基础的几种）
6. IEntity、Converter接口的定制化机制
