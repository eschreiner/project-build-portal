$().ready(function() {
	$('input[type!=hidden].autofocus').focus();
	$('.hidden-controls').hover(handleHiddenControls);
	
	$('.activateMilestone').click(activateMilestone);
});

function handleHiddenControls(e){
	$(this).children('.controls').toggle(e.type === 'mouseenter');
	if (e.type === 'mouseleave') {
		$('#shelfSelector').fadeOut();
	}
};

function activateMilestone(event) {
	event.stopPropagation();
	var root = $(event.target).closest('.hidden-controls');
	var caption = root.find('> .btn');
	$.ajax({
		url:"/milestone/activate/"+caption.attr('data-milestone-id'),
		type:"POST",
		dataType: 'text',
		success: function(msg) {
			caption.addClass('btn-success');
			reportSuccess(msg);
			reloadProject(caption.attr('data-project-id'));
			$('#milestoneList').load('/milestoneList/'+caption.attr('data-project-id'), function() {
				$('.hidden-controls').hover(handleHiddenControls);
				$('.activateMilestone').click(activateMilestone);
			});
		},
		error: function(msg,textStatus,errorThrown) {
			reportError(caption,msg,textStatus,errorThrown);
		}
	});
};

function reportSuccess(msg) {
	$("#messages-success").text(msg).fadeIn().delay(3000).fadeOut();
};
function reportError(button,msg,textStatus,errorThrown) {
	button.addClass('btn-warning');
	$("#messages-error").text(textStatus+" - "+errorThrown).fadeIn().delay(3000).fadeOut();
};

function reloadProject(projectID) {
	$("#project"+projectID).load('/projectInline/'+projectID, function() {
		var button = $("#project"+projectID).find(".btn"); 
		button.addClass("btn-updated").delay(1000).fadeIn(100,function() {
			$(this).removeClass("btn-updated");
		});
	});
};
