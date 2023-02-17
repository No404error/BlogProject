## 1.文章列表
#### 接口url：/articles
#### 请求方式：POST
#### 请求参数：
|   参数名称   |  参数类型   |    说明    |
|:--------:|:-------:|:--------:|
|   page   |   int   |   当前页数   |
| pageSize |   int   | 每页显示的数量  |
#### 响应数据：List\<ArticleVo>

## 2.热门文章
#### 接口url：/articles/hot
#### 请求方式：POST
#### 请求参数：无
#### 响应数据：List\<ArticleVo>

## 3.最新文章
#### 接口url：/articles/new
#### 请求方式：POST
#### 请求参数：无
#### 响应数据：List\<ArticleVo>


## 4.文章归档数据
#### 接口url：/articles/listArchives
#### 请求方式：POST
#### 请求参数：无
#### 响应数据：List\<Archive>


## 5.文章详情
#### 接口url：/articles/view/{id}
#### 请求方式：POST
#### 请求参数：
| 参数名称  | 参数类型  |  说明   |
|:-----:|:-----:|:-----:|
|  id   | long  | 文章id  |
#### 响应数据：ArticleVo


## 6.文章上传
#### 接口url：/articles/publish
#### 请求方式：POST
    private Long id;

    private ArticleBodyParam body;

    private CategoryVo category;

    private String summary;

    private List<TagVo> tags;

    private String title;
#### 请求参数：
| 参数名称 | 参数类型                                                    | 说明               |
| -------- | ----------------------------------------------------------- | ------------------ |
| title    | string                                                      | 文章标题           |
| id       | long                                                        | 文章id（编辑有值） |
| body     | object（{content: "ww", contentHtml: "ww↵"}）               | 文章内容           |
| category | {id: 2, avatar: "/category/back.png", categoryName: "后端"} | 文章类别           |
| summary  | string                                                      | 文章概述           |
| tags     | [{id: 5}, {id: 6}]                                          | 文章标签           |
#### 响应数据：ArticleVo