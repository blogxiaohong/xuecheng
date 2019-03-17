<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Hello World!</title>
</head>
<body>
Hello ${name}!
<br/>
遍历模型数据中的 stus
<br>
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>金额</td>
        <#--<td>出生日期</td>-->
    </tr>
    <#list stus as stu>
        <tr <#if stu_index % 2 == 0>style="background: cornflowerblue" </#if>>
            <td>${stu_index + 1}</td>
            <td>${stu.name}</td>
            <td>${stu.age}</td>
            <td>${stu.money}</td>
            <#--<td>出生日期</td>-->
        </tr>
    </#list>
</table>
<br>
遍历模型数据中的 stuMap
<br>
第一种方式：
<br>
姓名：${stuMap['stu1'].name}<br>
年龄：${stuMap['stu1'].age}<br>
<br>
第二种方式：
<br>
姓名：${stuMap.stu1.name}<br>
年龄：${stuMap.stu1.age}<br>
<br>
遍历 stuMap 的 key
<br>
<#list stuMap?keys as k>
    姓名：${stuMap[k].name}<br>
    年龄：${stuMap[k].age}<br>
</#list>
</body>
</html>