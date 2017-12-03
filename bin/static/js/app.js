function resetPassword() {
	var getUrl = window.location;
	var url = getUrl.protocol + "//" + getUrl.host;
	var userData = {
		email : $("#email").val()
	}
	$.ajax({
		url : url + "/api/reset",
		contentType : "application/json",
		type : 'POST',
		data : JSON.stringify(userData),
		success : function(result) {
			if (result.status) {
				showSuccesMessage();
				$('#mprogress').modal('hide');
				setTimeout("window.location.reload(true)", 1000);
			} else {
				showErrorMessage();
				$('#mprogress').modal('hide');
			}
		},
		error : function(e) {
			showServerMessage();
			$('#mprogress').modal('hide');
		}
	});

}

function showSuccesMessage() {
	$.notify({
		title : "Well done : ",
		message : "Action performed successfully!",
		icon : 'fa fa-check'
	}, {
		type : "success"
	});
}

function showErrorMessage() {
	$.notify({
		title : "Error: ",
		message : "some error occur when processing your request",
		icon : 'fa fa-check'
	}, {
		type : "warning"
	});
}

function showServerMessage() {
	$.notify({
		title : "Oups Oups: ",
		message : "The ressource you request may not exist on server",
		icon : 'fa fa-check'
	}, {
		type : "warning"
	});
}

// delete user
function deleteUser() {
	var userData = {
		id : $("#userId").val()
	}
	$.ajax({
		url : url + "/api/user",
		contentType : "application/json",
		type : 'DELETE',
		data : JSON.stringify(userData),
		success : function(result) {
			if (result.status) {
				$('#delete').modal('hide');
				showSuccesMessage();
				setTimeout("window.location.reload(true)", 1000);
			} else {
				$('#delete').modal('hide');
				showErrorMessage();
			}
		},
		error : function(e) {
			$('#delete').modal('hide');
			showServerMessage();
		}
	});
}

function deleteRole() {
	var roleData = {
		id : $("#modalRoleId").val()
	}
	$.ajax({
		url : url + "/api/role",
		contentType : "application/json",
		type : 'DELETE',
		data : JSON.stringify(roleData),
		success : function(result) {
			if (result.status) {
				$('#delete').modal('hide');
				showSuccesMessage();
				setTimeout("window.location.reload(true)", 1000);
			} else {
				$('#delete').modal('hide');
				showErrorMessage();
			}
		},
		error : function(e) {
			$('#delete').modal('hide');
			showServerMessage();
		}
	});
}
function addUser() {
	var userData = {
		phone : $("#phoneAdd").val(),
		fullName : $("#fullNameAdd").val(),
		password : $("#passwordAdd").val(),
		passwordConfirm : $("#repasswordAdd").val(),
		email : $("#emailAdd").val()
	}
	$.ajax({
		url : url + "/api/user",
		contentType : "application/json",
		type : 'POST',
		data : JSON.stringify(userData),
		success : function(result) {
			if (result.status) {
				$('#add').modal('hide');
				showSuccesMessage();
				setTimeout("window.location.reload(true)", 1000);
			} else {
				$('#add').modal('hide');
				showErrorMessage();
			}
		},
		error : function(e) {
			$('#add').modal('hide');
			showServerMessage();
		}
	});
}

// add role
function addRole() {
	var roleData = {
		name : $("#nameAdd").val(),
		description : $("#descriptionAdd").val()
	}
	$.ajax({
		url : url + "/api/role",
		contentType : "application/json",
		type : 'POST',
		data : JSON.stringify(roleData),
		success : function(result) {
			if (result.status) {
				$('#add').modal('hide');
				showSuccesMessage();
				setTimeout("window.location.reload(true)", 1000);
			} else {
				$('#add').modal('hide');
				showErrorMessage();
			}
		},
		error : function(e) {
			$('#add').modal('hide');
			showServerMessage();
		}
	});
}

//delete category
function deleteCategory() {
	var categoryData = {
		id : $("#modalCategoryId").val()
	}
	$.ajax({
		url : url + "/api/category",
		contentType : "application/json",
		type : 'DELETE',
		data : JSON.stringify(categoryData),
		success : function(result) {
			if (result.status) {
				$('#delete').modal('hide');
				showSuccesMessage();
				setTimeout("window.location.reload(true)", 1000);
			} else {
				$('#delete').modal('hide');
				showErrorMessage();
			}
		},
		error : function(e) {
			$('#delete').modal('hide');
			showServerMessage();
		}
	});
}

// delete song
function deleteItem() {
	var itemData = {
		id : $("#modalItemId").val()
	}
	$.ajax({
		url : url + "/api/music",
		contentType : "application/json",
		type : 'DELETE',
		data : JSON.stringify(itemData),
		success : function(result) {
			if (result.status) {
				$('#delete').modal('hide');
				showSuccesMessage();
				setTimeout("window.location.reload(true)", 1000);
			} else {
				$('#delete').modal('hide');
				showErrorMessage();
			}
		},
		error : function(e) {
			$('#delete').modal('hide');
			showServerMessage();
		}
	});
}
//add song
function addItem() {
	var catData = {
		id : parseInt($("#categorySelect").val())
	}
	var data = {
		name : $("#nameAdd").val(),
		description : $("#descriptionAdd").val(),
		artistName : $("#artistNameAdd").val(),
		category : catData
	}
	$.ajax({
		url : url + "/api/music",
		contentType : "application/json",
		type : 'POST',
		data : JSON.stringify(data),
		success : function(result) {
			if (result.status) {
				$('#add').modal('hide');
				showSuccesMessage();
				setTimeout("window.location.reload(true)", 1000);
			} else {
				$('#add').modal('hide');
				showErrorMessage();
			}
		},
		error : function(e) {
			$('#add').modal('hide');
			showServerMessage();
		}
	});
}

