<div class="row">
    <div class="col-md-9 col-lg-9"></div>
    <div class="col-md-3 col-lg-3">
        <div class="input-group">
            <span class="input-group-addon"><i class="glyphicon glyphicon-filter"></i></span>
            <input class="pull-right form-control" type="search" ng-model="q" placeholder="filter accounts..." />
        </div>
    </div>
</div>
<div class="row">
    <div class="col-md-12 col-lg-12">
        <div style="max-height: 600px; overflow: auto">
            <table class="table table-condensed table-responsive">
                <thead>
                <tr>
                    <th>Code</th>
                    <th>Title</th>
                </tr>
                </thead>
                <tbody>
                <tr class="animate-repeat" ng-repeat="account in accounts | filter:q">
                    <td>{{account.code}}</td>
                    <td>{{account.title}}</td>
                </tr>
                <tr class="animate-repeat" ng-if="accounts.length == 0">
                    <td colspan="2">No data.</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>