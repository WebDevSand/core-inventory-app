/**
 * BASIC LOGIN STUFF
 */

var adminApp = angular.module("adminApp", ['ui.router']);

//image upload directive 
adminApp.directive('fileModel', ['$parse', function ($parse) {
	return {
		restrict: 'A',
		link: function(scope, element, attrs) {
			var model = $parse(attrs.fileModel);
			var modelSetter = model.assign;

			element.bind('change', function(){
				scope.$apply(function(){
					modelSetter(scope, element[0].files[0]);
				});
			});
		}
	};
}]);

//file upload service
adminApp.service('fileUpload', ['$http', function ($http) {
	this.uploadFileToUrl = function(file, uploadUrl){
		var fd = new FormData();
		fd.append('file', file);
		console.log();

		return $http.post(uploadUrl, fd, {
			transformRequest: angular.identity,
			headers: {'Content-Type': undefined},
			transformResponse: function(data, headers){
				return data;}
		});

	}
}]);

adminApp.config(function($stateProvider, $urlRouterProvider) {

	$urlRouterProvider.otherwise("/managerLogin");

	$stateProvider
	.state("managerLogin",{
		url: "/managerLogin",
		templateUrl: "partials/managerLogin.html",
		controller: "AdminCtrl as auth"
	})
	.state("adminMainPage", {
		url: "/adminMainPage",
		templateUrl: "partials/manager_dashboard.html",
		controller: "MainPageCtrl as adminMainPage"
	})
	.state("manageInventoryHome",{
		url: "/manageInventoryHome",
		templateUrl: "partials/manageInventoryHome.html"
	})
	.state("manageInventoryHome.addNewInventory",{
		url: "/addNewInventory",
		templateUrl: "partials/addNewInventory.html",
		controller: "manageCtrl as manage"
	})
	.state("manageInventoryHome.add-remove",{
		url: "/add-remove",
		templateUrl: "partials/add-remove.html",
		controller: "addremoveCtrl as AR"
	})
	.state("manageInventoryHome.viewCurrentInventory",{
		url: "/viewCurrentInventory",
		templateUrl: "partials/viewCurrentInventory.html",
		controller: "invCtrl"
	})
	.state("managerLogout", {
		url: "/managerLogin",
		templateUrl: "partials/viewCurrentInventory.html",
		controller: "ManagerLogout"
	})
	.state("customers",{
		url: "/man-show-all-custs",
		templateUrl: "partials/man-show-all-custs.html",
		controller: "showAllCustsController"
	})
	.state("dashboard",{
		url: "/dashboard",
		templateUrl: "partials/manager_dashboard.html"
	})
	.state("adminInfo",{
		url: "/man-show-info",
		templateUrl: "partials/man-show-info.html",
		controller: "manShowInfoController"
	})
	.state("sales",{
		url: "/manSales",
		templateUrl: "partials/manSales.html",
		controller: "sCtrl"
	});

	console.log("IN HERE");

});


adminApp.controller("sCtrl", function($http, $scope){
	console.log("in salesCtrl");
	$scope.sortType = "id";
	$scope.sortReverse = false;
	$scope.searchItem = "";
	$scope.hide = 0;

	var order = this;
	$scope.orders = [];

	$http.get('rest/order/getAll').success( function(data){
		console.log(data);
		$scope.orderList = data;
		$scope.orderList.forEach(function(o){
			$http.get("rest/lineitem/getAllByOrderId?id=" + o.id).success(function(response){
				itemlist = response;
				var total = 0;
				itemlist.forEach(function(item){
					total+= (item.quantity * item.inventoryItem.unitPrice);
				});
				o.total = total;
			});
		});
	});


	$scope.showOrder = function(order){
		$scope.hide = 1;
		$scope.order = order;
		$scope.order.id = order.id;
		console.log("called");
		$http.get('rest/lineitem/getAllByOrderId?id='+$scope.order.id).then(function(response){
			console.log(response);
			$scope.lineitems = response.data;
			$scope.order.show = true;
		});
	}	
	$scope.goBack = function(){
		$scope.hide = 0;
	}
});

adminApp.service("AdminService", function($http, $q) {
	console.log("in admin service");

	var service = this;
	console.log(service);

	service.admin = {
			id: -1,
			firstname : "",
			lastname : "",
			email: "",
			password: "",
			authenticated: false
	};

	service.getAdmin= function() {
		return service.admin;
	};

	console.log("before set admin = " + service.admin);

	service.setAdmin= function(data) {
		service.admin.id = data.id;
		service.admin.firstname = data.firstname;
		service.admin.lastname = data.lastname;
		service.admin.email = data.email;
		service.admin.password = data.password;
		service.admin.authenticated = data.authenticated;
	};

	console.log("after set admin = " + service.admin);

	service.resetAdmin = function(){
		service.admin={
				id: -1,
				firstname: "",
				lastname: "",
				email : "",
				password : "",
				authenticated : false
		}
	};

	service.loginAdmin = function() {
		console.log("in loginAdmin");
		var promise;
		promise = $http.post('rest/admin/auth', service.admin).then(
				function(response) {

					console.log(response.data);
					return response;
				},
				function(error) {
					console.log('login user promise failed');
					return $q.reject(error);
				});

		return promise;
	};
});

adminApp.controller("AdminCtrl", function(AdminService,$state,$rootScope) {
	console.log("in AdminCtrl");
	$rootScope.authenticated = false;
	var login = this;
	console.log(login);
	login.admin = AdminService.getAdmin();

	login.doLogin = function() { 
		console.log("within the doLogin");

		var promise = AdminService.loginAdmin();
		promise.then(
				function(response) {
					console.log("Response: "+ response);
					if(response.data.id !== -1){
						login.admin.authenticated = true;
						$rootScope.authenticated = true;
						AdminService.setAdmin(response.data);
						console.log("setting user in login ctrl")
						console.log(AdminService.getAdmin());
						$state.go("adminMainPage");
					}
					else{
						alert("Invalid login!");
					}

				},
				function(error) {
					console.log("Error: " + error);
				}
		)
	}
});

/*
 * Main Page
 */


adminApp.controller("MainPageCtrl", function($state){
	console.log("in adminMainPage");
});

adminApp.controller("ManagerLogout", function($rootScope,$state,AdminService) {
	$rootScope.authenticated = false;
	AdminService.resetAdmin();
	$state.go('managerLogin');
});

/*
 * INVENTORY STUFF
 */
adminApp.service("InventoryService", function($http, $q){

	var service = this;

	service.item = {
			id: -1,
			name: " ",
			description: " ",
			unitPrice: -1,
			quantity: -1,
			department: null,
			discount: null,
			image: " "
	};

	service.getItem = function(){
		return service.item;
	}


	service.setItem = function(data){
		service.item.name = data.name;
		service.item.description = data.description;
		service.item.unitPrice = data.unitPrice;
		service.item.quantity = data.quantity;
		service.item.image = data.image
		console.log("setting data");
		console.log(service.item);
	}


	service.uploadPic = function(){
		var promise; 
	}

	service.createItem = function(){
		var promise;
		service.item = InventoryService.setItem();
		console.log("in create item");
		console.log(service.item);
		promise = $http.post("rest/inventoryitem/create", service.item).then(
				function(response){
					console.log(response);
					return response;
				},
				function(error){
					console.log("ERROR")
					return error;
				}

		);
		return promise;
	}


});

adminApp.controller("manageCtrl", function(InventoryService, $scope, $http, fileUpload){
	console.log("in manageCtrl");

	$scope.name = "";
	$scope.description = "";
	$scope.unitPrice;
	$scope.quantity;
	$scope.image;

	$scope.displayItem = function(){
		console.log($scope.name + " " + $scope.unitPrice)
	}

	$scope.item = {
			id: -1,
			name: $scope.name,
			description: $scope.description,
			unitPrice: $scope.unitPrice,
			quantity: $scope.quantity,
			departmentid: $scope.chosenDepartment,
			discountid: -1,
			image: $scope.image
	};

	var setDepartments = function(){
		$http.get("rest/department/getAll").then(function(response){
			$scope.departments = response.data;
		})

	}
	setDepartments();


	var inventory = this;



	inventory.addDepartment = function(){
		var department = {
				"id" : -1,
				"name" : $scope.depname
		}
		$http.post("rest/department/create", department).then(function(response){
			$scope.departments.push(response.data);
			$scope.depname = "";
			console.log("department "+response.data.name+" added");
		})
	}
	inventory.addItem = function(){

		//if file wants to be included
		if($scope.image != null){
			var file = $scope.image;
			console.log('file is ' );
			console.dir(file);
			var uploadUrl = "rest/inventoryitem/upload";
			var promise = fileUpload.uploadFileToUrl(file, uploadUrl);
			var s3UrlString = "";
			promise.then(function(response){
				s3UrlString = response.data;
				inventory.item = {
						id: -1,
						name: $scope.name,
						description: $scope.description,
						unitPrice: $scope.unitPrice,
						quantity: $scope.quantity,
						departmentid: $scope.chosenDepartment,
						discountid: -1,
						image: s3UrlString
				};


				$http.post("rest/inventoryitem/create", inventory.item).then(function(response){
					console.log("successfully uploaded with image");
					console.log(response.data);
				})
			})
			.catch(function(response){
				return response.data;
			});
		} //do this if no file included
		else{
			inventory.item = {
					id: -1,
					name: $scope.name,
					description: $scope.description,
					unitPrice: $scope.unitPrice,
					quantity: $scope.quantity,
					departmentid: $scope.chosenDepartment,
					discountid: -1,
					image: null
			};
			console.log("adding");
			console.log(inventory.item);
			console.log($scope.chosenDepartment);

			$http.post("rest/inventoryitem/create", inventory.item).then(function(response){
				console.log("successfully added");
				console.log(response.data);
			})
		}
	}

});



adminApp.controller("invCtrl", function($scope,$http){
	console.log("in inv controller");
	$scope.sortType = "id";
	$scope.sortReverse = false;
	$scope.searchItem = "";

	$http.get('rest/inventoryitem/getAll').success(function(data){
		$scope.itemlist = data;
		$http.get('rest/department/getAll').success(function(data){
			var departments = data;
			$scope.itemlist.forEach(function(item){
				departments.forEach(function(d){
					if(d.id == item.departmentid){
						item.department = d;
					}
				});
			});
		});
		console.log(data);
	})

});


adminApp.service("AddRemoveService", function($http){
	var service = this;



	service.discount = {
			discountID: -1,
			discount_Type: -1,
			amount: -1,
			description: " ",
			startDate: null,
			endDate: null
	}

	service.getDiscount = function(){
		console.log(service.discount);
		return service.discount;
	}
	service.setDiscount = function(data){
		service.discount.discountID = data.discountID;
		service.discount.discount_Type = data.discount_Type;
		service.discount.amount = data.amount;
		service.discount.description = data.description;
		service.discount.startDate = data.startDate;
		service.discount.endDate = data.endDate;
		console.log(service.discount);
	}


})

adminApp.controller("addremoveCtrl", function(AddRemoveService, $scope,$http,$state){

	$scope.discount;
	console.log("in add-remove controller");

	var inventory = this;

	$scope.sortType = "id";
	$scope.sortReverse = false;
	$scope.searchItem = "";
	$scope.edit = 0;
	$scope.discountYes = 0;
	$scope.discountNo = 0;


	inventory.id = -1;
	inventory.name = "";
	inventory.description = "";
	inventory.unitPrice = -1;
	inventory.quantity = -1;
	inventory.departmentid = -1;
	inventory.discountid = -1;
	inventory.image = null;





	$scope.change = function(data){

		inventory.id = data.id;
		inventory.name = data.name;
		inventory.departmentid = data.departmentid;
		inventory.discountid = data.discountid
		inventory.image = data.image;
		inventory.unitPrice = data.unitPrice;
		inventory.quantity = data.quantity;
		inventory.description = data.description;	
		$scope.edit = 1;
		
		
		if(inventory.discountid != -1){
			$scope.discountYes = 1;
		}
		else{
			$scope.discountNo = 1;
			$scope.perDiscount = 0;
			$scope.fDiscount = 0;
		}

	}
	$scope.reset = function(){
		$scope.edit = 0;
		$scope.discountYes = 0;
		$scope.discountNo = 0;
	}

	var resetData = function(){

		$http.get("rest/inventoryitem/getAll").success(function(data){
			$scope.itemlist = data;		

		});
	}

	resetData();


	$http.get("rest/inventoryitem/getAll").success(function(data){
		$scope.itemlist = data;
		console.log(data);

	})

	$scope.showReview = function(item){
		console.log("called");
		$http.get('rest/productreview/getAllByInventoryItemId?id=' + item.id).then(function(response){
			console.log(response);
			item.productreviews = response.data;
			item.show = true;
		});
		$http.get('rest/productreview/getAverageReviewFromItemId?id=' + item.id).then(function(response){
			console.log(response);
			item.averagereview = response.data;
		});
	}


	var something;
	$scope.applyItem = function(){

		if($scope.percentDiscount != null || $scope.flatDiscount != null){
			$scope.testDiscount = 0;
			var discount = this;
			if($scope.percentDiscount != null){
				discount.discountType = 1;
				discount.discountAmount = ($scope.percentDiscount/100);
			}
			if($scope.flatDiscount != null){
				discount.discountType = 0;
				discount.discountAmount = $scope.flatDiscount;
				if($scope.flatDiscount > inventory.unitPrice){
					alert("Cannot have the discount be greater than the unit price");
					return;
				}
			}
			$scope.date = new Date();
			discount.startDate = $scope.date
			discount.disDescription = $scope.pDiscountReason;
			$scope.discount = {
					discountID: -1,
					discount_Type: discount.discountType,
					amount: discount.discountAmount,
					description: discount.disDescription,
					startDate: discount.startDate
			}
			var newDiscount = $scope.discount;
			$scope.editItem(newDiscount);
			return;
		}
		if($scope.removeDiscount === true){
			console.log("We in removing");
			var discount = this;

			console.log(inventory.discountid);

			var originalPrice;
			$http.get("rest/discount/getAll").success(function(data){
				for(var i = 0; i < data.length; i++){
					if(inventory.discountid == data[i].discountID){
						var discountID = data[i].discountID;
						var oldDiscount = data[i].amount;
						var oldDescription = data[i].description;
						var theID = data[i].discount_Type;
						console.log(theID);
						var startDate = data[i].startDate;

					}
				}
				$scope.date = new Date();
				discount.endDate = $scope.date
				$scope.discount = {
						discountID: discountID,
						discount_Type: theID,
						amount: oldDiscount,
						description: oldDescription,
						startDate: startDate,
						endDate: discount.endDate
				}
				$scope.inventory = {
						id: inventory.id,
						name: inventory.name,
						description: inventory.description,
						unitPrice: inventory.unitPrice,
						quantity: inventory.quantity,
						departmentid: inventory.departmentid,
						discountid: inventory.discountid,
						image: inventory.image

				}
				console.log($scope.inventory);
				var revInventory = $scope.inventory;
				var revDiscount = $scope.discount;
				console.log(revDiscount);
				$scope.removeItem(revInventory,revDiscount);
				console.log(revInventory,revDiscount);
				return;
			});

		}

		$scope.editItem(inventory);
	}

	$scope.removeItem = function(inv,dis){

		if(dis.discount_Type == 0){
			inv.unitPrice = inv.unitPrice + dis.amount;
		}
		if(dis.discount_Type == 1){
			inv.unitPrice = inv.unitPrice + (inv.unitPrice * dis.amount);
		}


		$http.post("rest/inventoryitem/removeDiscount", inv,dis).then(function(response){
			console.log(inv);
			console.log(dis);
			console.log(response);

			$scope.reset();
			resetData();
		});



	}

	$scope.editItem = function(data){

		console.log("New discount created?");
		//AddRemoveSerivce.setDiscount(data);
		
		//console.log("In the Post: " + AddRemoveService.getDiscount());
		if($scope.newName != null){
			inventory.name = $scope.newName;
		}
		if($scope.newUnitPrice != null){
			if($scope.newUnitPrice < 0){
				$scope.newUnitPrice = 0;
			}
			inventory.unitPrice = $scope.newUnitPrice;
		}
		if(data.discount_Type == 0 && data.discountID != -1){
			inventory.unitPrice = inventory.unitPrice - data.amount;
			console.log(inventory.unitPrice);
		}
		if(data.discount_Type == 1 && data.discountID != -1){
			inventory.unitPrice = inventory.unitPrice - (inventory.unitPrice * data.amount);
		}
		if($scope.newQuantity != null){
			if($scope.newQuantity < 0){
				$scope.newQuantity = 0;
			}
			console.log($scope.newQuantity);
			inventory.quantity = $scope.newQuantity;
		}
		if($scope.newDescription != null){
			inventory.description = $scope.newDescription;
		}
		
		if($scope.perDiscount || $scope.fDiscount){
			$http.post('rest/discount/create',data).then(function(response){
				inventory.item = {
						id: inventory.id,
						name: inventory.name,
						description: inventory.description,
						unitPrice: inventory.unitPrice,
						quantity: inventory.quantity,
						departmentid: inventory.departmentid,
						discountid: response.data.discountID,
						image: inventory.image	
				}
				
				$http.post("rest/inventoryitem/update", inventory.item).then(function(data){
					console.log(data);
					console.log("Item updated successfully?")
					$scope.reset();
					resetData();
					return;
				}) 
			});
		}else{
			inventory.item = {
					id: inventory.id,
					name: inventory.name,
					description: inventory.description,
					unitPrice: inventory.unitPrice,
					quantity: inventory.quantity,
					departmentid: inventory.departmentid,
					discountid: -1,
					image: inventory.image	
			}
			
			$http.post("rest/inventoryitem/update", inventory.item).then(function(data){
				console.log(data);
				console.log("Item updated successfully?")
				$scope.reset();
				resetData();
				return;
			}) 
		}
		
	}










});



/**
 * CUSTOMER DATA
 */


adminApp.controller('showAllCustsController', function($scope,$http) {
	$scope.sortType = "lastname";
	$scope.sortReverse = false;
	$scope.searchCustomer = "";

	$http.get('rest/customer/getAll').success(function(data){
		$scope.allCusts = data;
	});


});

/*
 * ADMIN INFO
 */


adminApp.controller('manShowInfoController', function($scope,$http,AdminService) {
	admin = AdminService.getAdmin();
	$scope.updateShow = false;
	$scope.message = "";
	$scope.messageClass = "";
	$scope.manInfo = {
			firstName: admin.firstname,
			lastName: admin.lastname,
			email: admin.email
	}
	$scope.updateInfo = function(){
		admin.firstname = $scope.manInfo.firstName;
		admin.lastname = $scope.manInfo.lastName;
		admin.email = $scope.manInfo.email;

		AdminService.setAdmin(admin);

		$http.post('rest/admin/update', admin).then(function(response){
			$scope.message = "Info Updated."
				$scope.messageClass = "alert-success";
			$scope.updateShow = false;
		});
	};

	$scope.updatePass = function(){
		if($scope.pass1 != $scope.pass2){
			$scope.message = "New Passwords must match!";
			$scope.messageClass = "alert-danger";
		}
		else if($scope.currPass != admin.password){
			$scope.message = "Incorrect current password!"
				$scope.messageClass = "alert-danger";
		}
		else{
			admin.password = $scope.pass1;
			$http.post('rest/admin/update', admin).then(function(response){
				AdminService.setAdmin(admin);
				$scope.passShow = false;
				$scope.message = "Password Updated."
					$scope.messageClass = "alert-success";
			});
		}
	};

});

adminApp.controller("salesCtrl", function($http, $scope){
	$scope.sortType = "id";
	$scope.sortReverse = false;
	$scope.searchItem = "";
	$scope.hide = 0;

	var order = this;

	$http.get("rest/order/getAll").success(function(data){

		$scope.orderList = data;
		$scope.orderList.forEach(function(o){
			$http.get("rest/lineitem/getAllByOrder?id =" + order.id).success(function(response){
				$scope.itemlist = response;
				var total = 0;
				$scope.lineitems.forEach(function(item){
					total+= (item.quantity * item.inventoryItem.unitPrice);
				});
				o.total = total;
			});
		});
	});

	$scope.change = function(data){
		$scope.hide = 1;
		order.id = data.id;
		console.log(order.id);
		$http.get("rest/lineitem/getAllByOrder?id =" + order.id).success(function(response){
			$scope.itemlist = response;
			var total = 0;
			$scope.lineitems.forEach(function(item){
				total+= (item.quantity * item.inventoryItem.unitPrice);
			});
			data.total = total;
		});

	};

	$scope.goBack = function(){
		$scope.hide = 0;


	}

});