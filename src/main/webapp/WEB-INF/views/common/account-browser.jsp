<div id="{{handler}}" class="modal fade" style="z-index: 99999">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">{{header}}</h4>
            </div>
            <div class="modal-body">

                <p class="text-warning">{{body}}</p>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" data-ng-click="callbackbuttonright(); $event.stopPropagation()">Launch Alert</button>

            </div>
        </div>
    </div>
</div>