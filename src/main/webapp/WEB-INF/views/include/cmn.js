function test(){
alert("Hi");
}

$('.datepickerclass').daterangepicker({
			"autoUpdateInput" : false,
			singleDatePicker : true,
			selectMonths : true,
			selectYears : true,
			locale : {
				format : 'DD-MM-YYYY'
			}
		});
		$('input[name="dob"]').on('apply.daterangepicker',
				function(ev, picker) {
					$(this).val(picker.startDate.format('DD-MM-YYYY'));
				});

		$('input[name="dob"]').on('cancel.daterangepicker',
				function(ev, picker) {
					$(this).val('');
				});
		//daterange-basic_new
		// Basic initialization
		$('.daterange-basic_new').daterangepicker({
			applyClass : 'bg-slate-600',

			cancelClass : 'btn-light',
			locale : {
				format : 'DD-MM-YYYY',
				separator : ' to '
			}
		});
		 $('.maxlength-options').maxlength({
	            alwaysShow: true,
	            threshold: 10,
	            warningClass: 'text-success form-text',
	            limitReachedClass: 'text-danger form-text',
	            separator: ' of ',
	            preText: 'You have ',
	            postText: ' chars remaining.',
	            validate: true
	        });
		 
		 $('.maxlength-badge-position').maxlength({
	            alwaysShow: true,
	            placement: 'top'
	        });
		 /* Block the card on button click */
		 $('#submtbtn1').on('click', function() {
		 	var block = $(this).parent().parent();
		 	$(block).block({ 
		 	    message: '<i class="icon-spinner4 spinner"></i>',
		 	    timeout: 2000, //unblock after 2 seconds
		 	    overlayCSS: {
		 	        backgroundColor: '#fff',
		 	        opacity: 0.8,
		 	        cursor: 'wait'
		 	    },
		 	    css: {
		 	        border: 0,
		 	        padding: 0,
		 	        backgroundColor: 'transparent'
		 	    }
		 	});
		 });
		  // Default message
	        $('#data_view_button11').on('click', function() {
	            var block = $(this).closest('.card');
	            $(block).block({
	                message: '<span class="font-weight-semibold">Please wait...</span>',
	                timeout: 2000, //unblock after 2 seconds
	                overlayCSS: {
	                    backgroundColor: '#fff',
	                    opacity: 0.8,
	                    cursor: 'wait'
	                },
	                css: {
	                    border: 0,
	                    padding: 0,
	                    backgroundColor: 'transparent'
	                }
	            });
	        });
		  $('#submtbtn').on('click', function() {
	            $.blockUI({ 
	                message: '<i class="icon-spinner4 spinner"></i>',
	                fadeIn: 800, 
	                timeout: 2000, //unblock after 2 seconds
	                overlayCSS: {
	                    backgroundColor: 'transparent',
	                    opacity: 0.2,
	                    zIndex: 1200,
	                    cursor: 'wait'
	                },
	                css: {
	                    border: 0,
	                    color: '#fff',
	                    zIndex: 1201,
	                    padding: 0,
	                    backgroundColor: 'transparent'
	                },
	                onBlock: function() { 
	                    alert('Page is now blocked. FadeIn completed.'); 
	                } 
	            });
	        });

		/*  $('#submtbtn').on('click', function() {
					
				  //  message: '&lt;i class="icon-spinner4 spinner">&lt;/i>',
				    timeout: 4000, //unblock after 2 seconds
				    overlayCSS: {
				        backgroundColor: '#1b2024',
				        opacity: 0.8,
				        cursor: 'wait'
				    },
				    css: {
				        border: 0,
				        color: '#fff',
				        padding: 0,
				        backgroundColor: 'transparent'
				    }
				});
			}); */