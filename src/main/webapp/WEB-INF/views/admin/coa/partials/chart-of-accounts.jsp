<div class="row">
    <div class="col-md-12 col-lg-12">
        <button class="btn btn-primary" onclick="(window.location.href = '#/new')">Add</button>
    </div>
</div>
<div class="row">
    <div class="col-md-9 col-lg-9"></div>
    <div class="col-md-3 col-lg-3">
        <div class="input-group">
            <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
            <input class="pull-right form-control" type="search" ng-model="q" placeholder="filter accounts..." />
        </div>
    </div>
</div>
<div style="margin-top: 20px;"></div>
<div class="row">
    <div class="col-md-2 col-lg-2"><span style="font-weight: bold; padding-left: 7px;">Code</span></div>
    <div class="col-md-7 col-lg-7"><span style="font-weight: bold">Title</span></div>
    <div class="col-md-3 col-lg-3"><span style="font-weight: bold">Type</span></div>
</div>
<div class="row">
    <div class="col-md-12 col-lg-12">
        <div style="max-height: 600px; overflow: auto">
            <table class="table table-condensed table-responsive">
                <tbody>
                <tr class="animate-repeat" ng-repeat="account in accounts | filter:q as results">
                    <td class="code-col">{{account.code}}</td>
                    <td>{{account.title}}</td>
                    <td class="type-col">{{account.type}}</td>
                </tr>
                <tr class="animate-repeat" ng-show="results.length == 0">
                    <td colspan="2">No records found.</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>