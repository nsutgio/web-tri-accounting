<div style="height: 600px; overflow: auto">
    <div class="row" style="padding-left: 10px;">
        <table class="table table-condensed table-responsive">
            <thead>
            <tr>
                <th>Code</th>
                <th>Title</th>
            </tr>
            </thead>
            <tbody>
            <tr class="animate-repeat" ng-repeat="account in accounts">
                <td>{{account.code}}</td>
                <td>{{account.title}}</td>
            </tr>
            </tbody>
        </table>
    </div>
</div>