<div class="row">
    <div class="col-md-12 col-lg-12">
        <button class="btn btn-primary" onclick="(window.location.href = '#/new')">Add</button>
    </div>
</div>
<div class="row">
    <div class="col-md-12 col-lg-12">
            <div id="search-div" class="col-md-4 col-lg-4">
            </div>
            <div class="col-md-8 col-lg-8">
                <div class="pull-right">
                    <button ng-init="init()" ng-click="accounts_tree.expand_all()" class="btn btn-default btn-sm">Expand All</button>
                    <button ng-click="accounts_tree.collapse_all()" class="btn btn-default btn-sm">Collapse All</button>
                </div>
            </div>
    </div>
</div>
<div class="row">
    <div ng-show="true">
        <tree-grid tree-data="tree_data" tree-control="accounts_tree" col-defs="col_defs" expand-on="expanding_property"
                   on-select="accounts_tree_handler(branch)" expand-level="5" icon-expand="glyphicon glyphicon-tag"
                   icon-collapse="glyphicon glyphicon-tags" icon-leaf="glyphicon glyphicon-list-alt"></tree-grid>
    </div>
</div>