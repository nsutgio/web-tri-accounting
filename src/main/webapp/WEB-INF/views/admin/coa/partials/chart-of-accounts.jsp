<div class="row">
    <div class="col-md-12 col-lg-12">
        <button class="btn btn-primary" onclick="(window.location.href = '#/new')">Add</button>
    </div>
</div>
<div style="margin-top: 20px;"></div>
<div class="row">
    <div class="col-md-12 col-lg-12">
        <div ng-model="showMinDir" ng-value="false">
            <div ng-show="!showMinDir">
                <div class="row">
                    <div id="search-div" class="col-md-4 col-lg-4">
                    </div>
                    <div class="col-md-8 col-lg-8">
                        <div id="collapse-btns" class="pull-right">
                            <button data-ng-init="init()" ng-click="accounts_tree.expand_all()" class="btn btn-default btn-sm">Expand All</button>
                            <button ng-click="accounts_tree.collapse_all()" class="btn btn-default btn-sm">Collapse All</button>
                        </div>
                    </div>
                </div>
                <tree-grid tree-data="tree_data" tree-control="accounts_tree" col-defs="col_defs" expand-on="expanding_property" on-select="accounts_tree_handler(branch)" expand-level="2" icon-leaf= "glyphicon glyphicon-globe"></tree-grid>
            </div>
            <div style="margin-top: 20px;"></div>
            <div ng-show="showMinDir">
                <tree-grid tree-data="tree_data"></tree-grid>
            </div>
        </div>
    </div>
</div>
<%--<div class="row">--%>
    <%--<div class="col-md-9 col-lg-9"></div>--%>
    <%--<div class="col-md-3 col-lg-3">--%>
        <%--<div class="input-group">--%>
            <%--<span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>--%>
            <%--<input class="pull-right form-control" type="search" ng-model="q" placeholder="filter accounts..." />--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>
<%--<div style="margin-top: 20px;"></div>--%>
<%--<div class="row">--%>
    <%--<div class="col-md-2 col-lg-2"><span style="font-weight: bold; padding-left: 7px;">Code</span></div>--%>
    <%--<div class="col-md-6 col-lg-6"><span style="font-weight: bold; padding-left: 80px;">Title</span></div>--%>
    <%--<div class="col-md-3 col-lg-3"><span style="font-weight: bold">Type</span></div>--%>
    <%--<div class="col-md-1 col-lg-1"><span style="font-weight: bold">&nbsp;</span></div>--%>
<%--</div>--%>
<%--<div class="row">--%>
    <%--<div class="col-md-12 col-lg-12">--%>
        <%--<div style="max-height: 600px; overflow: auto">--%>
            <%--<table class="table table-condensed table-responsive table-hover">--%>
                <%--<tbody>--%>
                <%--<tr class="animate-repeat" ng-repeat="account in accounts | filter:q as results">--%>
                    <%--<td nowrap class="code-col"><span ng-bind-html="unsanitizedHtml(account.code)"/></td>--%>
                    <%--<td nowrap><span ng-bind-html="unsanitizedHtml(account.title)"/></td>--%>
                    <%--<td class="type-col">{{account.accountType.description}}</td>--%>
                    <%--<td ng-show="loaded" style="font: 14px;">--%>
                        <%--<a title="View" href="#/account/{{account.id}}"><i class="fa fa-search"></i></a>--%>
                        <%--<a title="Edit" href="#/account/{{account.id}}/edit">&nbsp;&nbsp;<i class="fa fa-edit"></i></a>--%>
                    <%--</td>--%>
                <%--</tr>--%>
                <%--<tr class="animate-repeat" ng-show="results.length == 0">--%>
                    <%--<td colspan="2">No records found.</td>--%>
                <%--</tr>--%>
                <%--</tbody>--%>
            <%--</table>--%>
        <%--</div>--%>
    <%--</div>--%>
<%--</div>--%>
