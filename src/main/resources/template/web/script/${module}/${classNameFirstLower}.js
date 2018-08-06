<#assign className = table.className/>
<#assign tableName = tableChName!>
<#assign cNameFL = className?uncap_first/>
<#assign cNameLC = className?lower_case/>
;
page.${cNameFL} = system.initPage("${cNameFL}");
page.${cNameFL}.init = {
    initPage: function () {
        var page = window.page.${cNameFL}; // window 一定要加上，否则全局的page变量不是页面定义那一个
        this.initForm(page);
        this.initDg(page);
    },

    initForm: function (page) {
        system.initInput(page.fm);
        system.initBtn(page.root.find(".page-btn"),page.fucnName);
    },

    initDg: function (page) {
        adminUI.datagrid(page.dg, {
            url : '/${cNameFL}/queryPage',
            queryParams: {sortColumns: "id desc,cre_time desc"},
            columns: [[
                {field: 'ck', checkbox: true, width: 50},
                <#list table.columns as column>
                <#if column.isPkColumn>
                {field: '${column.columnNameLower}', title: '${column.columnAlias!}', width: 10, hidden: 'true'},
                <#elseif column.isNumberColumn && !column.contains(commonColumn)>
                {field: '${column.columnNameLower}', title: '${column.columnAlias!}', width: 100,
                    formatter: function (value, row, index) {
                        return numUtil.toThousands(value);
                    }
                },
                <#elseif column.isDateColumn>
                {field: '${column.columnNameLower}', title: '${column.columnAlias!}', width: ${column.isDateTimeColumn?string(150,100)}},
                <#elseif column.columnAlias?contains("(")>
                <#assign ds=column.columnAlias?index_of("(")>
                <#assign de=column.columnAlias?index_of(")")>
                {field: '${column.columnNameLower}', title: '${column.columnAlias?substring(0,ds)}', width: 100,
                    formatter: function (value, row, index) {
                        return system.getDictDesc("${column.columnAlias?substring(ds+1,de)}", value);
                    }
                },
                <#else>
                {field: '${column.columnNameLower}', title: '${column.columnAlias!}', width: 100},
                </#if>
                </#list>
                {field: 'oper', title: '操作', width: 400,
                    formatter: function (value, row, index) {
                        return system.getOptBtn({title: "修改", event: page.getFucnAllName("modify", [index])})
                            + system.getOptBtn({title: "详情", event: page.getFucnAllName("detail",[index])})
                            + system.getOptBtn({title: "删除", event: page.getFucnAllName("remove",[index])});
                    }
                }
            ]],
            onLoadSuccess: function (data) {
                console.log("加载成功");
            }

        });

    }
}

page.${cNameFL}.fucn = {
    query : function () {
        var page = window.page.${cNameFL};
        if(!adminUI.validateForm(page.fm)){
            return false;
        }
        var param = page.fm.serializeObject();
        adminUI.reloadDg(page.dg, param);
    },
    reset : function () {
        var page = window.page.${cNameFL};
        adminUI.clearForm(page.fm);
    },
    export : function () {
        var page = window.page.${cNameFL};
        if(!adminUI.validateForm(page.fm)){
            return false;
        }
        var url = "/${cNameFL}/export";
        var param = {
            sortColumns: "id desc,cre_time desc",
            downloadName : '${tableName}列表'
        };
        param = jq.extend(param, page.fm.serializeObject());
        system.download(url,param);
    },
    add: function () {
        var page = window.page.${cNameFL};
        window.page.action = "add";
        adminUI.openWindow(page,"${tableName}新增", "page/${module}/${cNameFL}_detail.html");
    },
    modify : function (index) {
        var page = window.page.${cNameFL};
        var row = system.getDgRow(page.dg,index);
        window.page.action = "modify";
        window.page.param = row;
        adminUI.openWindow(page,"${tableName}修改", "page/${module}/${cNameFL}_detail.html");
    },
    detail : function (index) {
        var page = window.page.${cNameFL};
        var row = system.getDgRow(page.dg,index);
        window.page.action = "detail";
        window.page.param = row;
        adminUI.openWindow(page,"${tableName}详情","page/${module}/${cNameFL}_detail.html");
    },
    remove: function (index) {
        var page = window.page.${cNameFL};
        var row = system.getDgRow(page.dg, index);
        if(!row){
            adminUI.alertInfo("请选择要删除的数据！");
            return false;
        }
        // 构造请求参数
        var param = {
            ids:[row.id]
        };
        adminUI.confirm("删除${tableName}","确认删除${tableName}?",function (r) {
            if(!r){
                return false;
            }
            ajax.postJson("/${cNameFL}/remove",param,function () {
                adminUI.alertInfo("删除成功");
                adminUI.reloadDg(page.dg);
            })
        })
    },
    removeChecked: function (index) {
        var page = window.page.${cNameFL};
        var rows = system.getDgCheckedRow(page.dg);
        if(!rows || rows.length==0){
            adminUI.alertInfo("请选择要删除的数据！");
            return false;
        }
        // 构造请求参数
        var param = {
            ids:[]
        };
        jq.each(rows,function (i,n) {
            param.ids.push(n.id);
        });
        console.log(param.ids);
        adminUI.confirm("删除${tableName}","确认删除${tableName}?",function (r) {
            if(!r){
                return false;
            }
            ajax.postJson("/${cNameFL}/remove",param,function () {
                adminUI.alertInfo("删除成功");
                adminUI.reloadDg(page.dg);
            })
        })
    }

};

jq(document).ready(function () {
    page.${cNameFL}.init.initPage();
});

