var buttonTextRunSqlMax = 'Run SQL Max';
$(document).ready(function() {
    var token = $("meta[name='_csrf']").attr("content");

    $("#tabs").tabs({
		select : function(event, ui) {
			hac.global.toggleActiveSidebar(ui.index + 1);
		}
	
	});


	$('#runSQLMax').click(function(e) {
		hac.global.notify("Running test...");
		$('#resultMax').fadeOut();
		$('#runSQLMax').html(buttonTextRunSqlMax + ' ' + hac.global.getSpinnerImg());
		$('#runSQLMax').attr('disabled', true);
		var url = $('#runSQLMax').attr('data-url');
		$.ajax({
			url : url,
			type : 'POST',
			headers : {
				'Accept' : 'application/json',
                'X-CSRF-TOKEN' : token
			},
			success : function(data) {
				debug.log(data);
				
				$('#durationAdded').html(data.durationAdded);
				$('#durationAddedMax').html(data.durationAddedMax);
				$('#durationAddedMaxIndex').html(data.durationAddedMaxIndex);
				$('#runSQLMax').html(buttonTextRunSqlMax);
				$('#resultMax').fadeIn();
				$('#runSQLMax').removeAttr('disabled');
			},
			error : hac.global.err
		});
	});
});