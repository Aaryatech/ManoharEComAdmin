$('.datatable-fixed-left_custom').DataTable({

			columnDefs : [ {
				orderable : false,
				targets : [ 1 ]
			}, {
				width : "10px",
				targets : [ 0 ]
			}, {
				width : "10px",
				targets : [ 1 ]
			}, {
				width : "200px",
				targets : [ 3 ]
			} ],
			//scrollX : true,
			scrollX : true,
			scrollY : '65vh',
			scrollCollapse : true,
			paging : true,
			fixedColumns : {
				leftColumns : 2,
				rightColumns : 1
			}

		});

		$(document).ready(function() {

			$('body').on('click', '#selAll', function() {
				//alert("111111");
				$('body input[type="checkbox"]').prop('checked', this.checked);
				// $(this).toggleClass('allChecked');
			})
		});