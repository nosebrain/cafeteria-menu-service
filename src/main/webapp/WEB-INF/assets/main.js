$(function() {
	$('.reload-cafeteria').click(function() {
		var button = $(this);
		var cafUrl = button.data('context') + button.data('uni_id') + '/' + button.data('caf_id') + '/' + button.data('week') + '.json';
		$.ajax({
			url: cafUrl,
			success: function() {
				location.reload();
			}
		});
		button.parent().parent().find('.label').removeClass('hidden');
	});
})