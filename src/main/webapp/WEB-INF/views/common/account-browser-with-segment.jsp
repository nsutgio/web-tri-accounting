<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<button title="browse account"  class="btn btn-primary" data-toggle="modal" data-target="#accountWithSegmentModal"> {{ btnLabel }}</button>

<div class="modal fade" id="accountWithSegmentModal" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg" style="width: 90%"  ng-app="cmnAccountBrowserWithSegmentApp">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                <h4 class="modal-title" id="myModalLabel">Browse accounts</h4>
            </div>
            <div class="modal-body">
                <div>
                    <div class="row">
                        <div class="input-group pull-right" style="width: 300px">
                            <input class="form-control" placeholder="Search" ng-model="query" />
                            <span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-lg-3 col-md-3">
                            <div class="row" style="margin-top: 15px; border-top: 1px solid #dcdcdc;">
                                <p style="font-weight: bold; padding-top: 10px;">Filter by segments</p>
                                <ul style="list-style: none; padding-left: 2px;">
                                    <li ng-repeat="segment in segments">
                                        <label style="font-weight: normal; cursor: pointer"><input ng-model="segment.selected" type="checkbox" ng-change="toggleSegment($index, segment)" /> {{segment.description}}</label>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="col-lg-9 col-md-9">
                            <div class="row-top-buffer" style="margin-top: 15px"/>
                            <div class='row' style="border-top: 1px solid #dcdcdc; padding-top: 10px">
                                <div class="col-md-2 col-lg-2"><span style="font-weight: bold; padding-left: 7px;">Code</span></div>
                                <div class="col-md-7 col-lg-7"><span style="font-weight: bold; padding-left: 80px;">Title</span></div>
                                <div class="col-md-2 col-lg-2"><span style="font-weight: bold">Type</span></div>
                            </div>
                            <div class="row-top-buffer" style="margin-top: 5px"/>
                            <div class="row" style='max-height: 500px; overflow: auto;'>
                                <div ng-show="!accounts">Loading accounts...</div>
                                <table class="table table-striped table-hover table-bordered">
                                    <thead>
                                    <tr>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr data-dismiss="modal" ng-repeat="account in accs = (accounts | filter:query)" style="cursor: pointer" ng-click="selectAccount(account)">
                                        <td class='code-col'>{{account.code}}</td>
                                        <td>{{account.title}}</td>
                                        <td class='type-col'>{{account.accountType.description}}</td>
                                    </tr>
                                    <tr ng-show="accs.length == 0"><td colspan="3" align="center">No records found</td></tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

</script>