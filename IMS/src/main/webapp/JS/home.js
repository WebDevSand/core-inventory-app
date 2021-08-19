var storeApp = angular.module("storeApp", ["ui.router"]);

storeApp.config(function($stateProvider, $urlRouterProvider) {
	console.log("init store app...");

	//The default route when nothing is selected
	$urlRouterProvider.otherwise('/mainStorePage');

	$stateProvider
	.state("mainStorePage", {
		url:"/mainStorePage",
		templateUrl:"partials/mainStorePage.html", //html
		controller: "MainCtrl as main",
	})
	.state("customerInfo", {
		url:"/cust-show-info",
		templateUrl:"partials/cust-show-info.html",
		controller: "custShowInfoController"
	})
	.state("cart", {
		url: "/cart",
		templateUrl: "partials/cust-cart.html",
		controller: "viewCartController"
	})
	.state("viewItem", {
		url: "/item",
		templateUrl: "partials/view-item.html",
		controller: "viewItemController"
	})
	.state("login", {
		url:"/login",
		templateUrl:"partials/login.html",
		controller: "LoginCtrl as login"
	})
	.state("confirmCheckout", {
		url: "/confirmCheckout",
		templateUrl: "partials/confirm-checkout.html"
	})
	.state("getPastOrders",{
		url: "/getOrders",
		templateUrl:"partials/cust-getOrders.html",
		controller: "getOrdersCtrl"
	});
});

storeApp.controller('MainCtrl', function($http, $scope,$rootScope,CustomerService,ItemService,ItemsService,$state) {
	// Begin filter
	$rootScope.searchTerms = "";
	$scope.orderBy = "id";
	$scope.sortType = "inventoryItem";
	$scope.sortReverse = false;
	// End filter
	$scope.cart = ItemsService.getCart();
	console.log($scope.cart);
	$rootScope.customer = CustomerService.getCustomer();
	$rootScope.departments = [];
	let allInvItems;
	$http.get('rest/inventoryitem/getAll').then(function(data) {
		allInvItems = data.data;
		$http.get('rest/department/getAll').then(function(response) {
			$rootScope.departments = response.data;
			$rootScope.departments.forEach(function(dept) {
				console.log("yeeeeboi");
				dept.items = [];
				dept.show = [];
				dept.index = -1;
				dept.count = 0;
				dept.increaseIndex = function(){if(dept.index + 1 != dept.items.length){dept.index++;console.log(dept.index);}};
				dept.reduceIndex = function(){if(dept.index - 1 != -1){dept.index--;console.log(dept.index);}};
			});
			allInvItems.forEach(function(item) {
				$rootScope.departments.forEach(function(dept) {
					if (item.departmentid === dept.id) {

						//manage image sizes to 250 x 250
						var i = new Image();
						var w = 0;
						var h = 0;
						var now = new Date().getTime();
						if(item.image != null){
							i.src = item.image;
							while(i.width > 250 || i.height > 250){
								i.width *= .75;
								i.height *= .75;
							}
							w = i.width;
							h = i.height;
						}
						var shrink_speed = (new Date().getTime() - now);

						//push item
						dept.count++;
						if(dept.count % 3 == 1){
							//add new item row
							dept.items.push([]);
							dept.show.push([]);
							dept.index++;
						}
						dept.show[dept.index].push(true);
						
						var inCart = false;
						$scope.cart.forEach(function(c){
							if(c.inventoryitemid == item.id && c.removed == false){
								dept.items[dept.index].push({
									id: item.id,
									name: item.name,
									unitPrice: item.unitPrice,
									quantity: item.quantity,
									department: dept,
									description: item.description,
									discountid: item.discountid,
									image: item.image,
									imageWidth: w,
									imageHeight: h,
									inCart: true
								});
								inCart = true;
							}
						});
						
						if(!inCart){
							dept.items[dept.index].push({
								id: item.id,
								name: item.name,
								unitPrice: item.unitPrice,
								quantity: item.quantity,
								department: dept.id,
								description: item.description,
								discountid: item.discountid,
								image: item.image,
								imageWidth: w,
								imageHeight: h,
								inCart: false
							});
						}
						
					}
				});
			});
			console.log($rootScope.departments);
			$rootScope.departments.forEach(function(dept) {
				dept.index = 0;
			});
		});
	});

	$scope.viewPage = function(item){
		ItemService.setItem(item);
		$state.go("viewItem");
	};
	//logout functionality
	$rootScope.logout = function () {
		console.log("within logout");
		$rootScope.authenticated = false;
		CustomerService.resetCustomer();
		$rootScope.customer = CustomerService.getCustomer();
		$state.go("mainStorePage");
	};
	

	//------------------------------------------Cart
	$rootScope.addItemToCart = function(item,q) {
		item.inCart = true;
		itemToAdd = {
				inventoryitemid: item.id,
				name: item.name,
				unitPrice: item.unitPrice,
				quantity: q
		};
		ItemsService.addToCart(itemToAdd);
		$scope.updateCart();
		console.log($scope.cart);
	};
	
	$scope.updateCart = function(){
		$scope.cart = ItemsService.getCart();
	}

	$scope.getNumberOfItemsInCart = function() {
		let numberOfItems = 0;
		for (i = 0; i < $scope.cart.length; i++) {
			numberOfItems += $scope.cart[i].quantity;
		}
		return numberOfItems;
	}
});


storeApp.service('ItemsService', function($http) {

	this.getItemByID = function(id) {
		$http.get('rest/inventoryitem/get?id='+id).then(function(response){
			return response.data;
		});
	}

	this.cart = [];

	this.emptyCart = function() {
		this.cart = [];
	}

	this.getCart = function() {
		return this.cart;
	};

	this.setCart = function(c){
		this.cart = c;
	};
	
	this.addToCart = function(lineItem) {
		this.cart.push(lineItem);
	}
	
	this.updateItemQuantity = function(item,chosenQuantity){
		for (i = 0; i < this.cart.length; i++) {
			if (this.cart[i].inventoryitemid === item.id) {
				this.cart[i].quantity = chosenQuantity;
				return true;
			}
		}
	};
	
	this.removeFromCart = function(item) {
		for (i = 0; i < this.cart.length; i++) {
			if (this.cart[i].inventoryitemid === item.inventoryitemid) {
				var index = this.cart.indexOf(item);
				if (index > -1) {
				    this.cart.splice(index, 1);
				}
				
				item.inCart = false;
				console.log("removed : "+this.cart);
				return true;
			}
		}
		console.log(" not removed");
		return false;
	};
	
	this.getQuantityIfIn = function(item){
		for (i = 0; i < this.cart.length; i++) {
			if (this.cart[i].inventoryitemid === item.id) {
				return this.cart[i].quantity;
			}
		}
		return 0;
	}

	this.createOrder = function(order) {
		let promise = $http.post('rest/order/create', order).then(
				function(response) {
					return response;
				},
				function(error) {
					return error;
				}
		);
		return promise;
	}

	this.createLineItem = function(order,item){
		let lineItem = {
				id: -1,
				orderid: order.id,
				quantity: item.quantity,
				inventoryitemid: item.inventoryitemid
		};
		console.log(lineItem);
		console.log(order);
		console.log(item);
		$http.post('rest/lineitem/create', lineItem).then(
				function(response) {
					console.log(response.data);
				},
				function(error) {
					return error;
				}
		);
	};
	
	this.createLineItems = function(order) {
		let promise;
		this.cart.forEach(function(item) {
			let lineItem = {
					id: -1,
					orderid: order.id,
					quantity: item.quantity,
					inventoryitemid: item.id
			}
			promise = $http.post('rest/lineitem/create', lineItem).then(
					function(response) {
						return response;
					},
					function(error) {
						return error;
					}
			);
		});
		return promise;
	};
});

// Service that handles all things relating to Customer
storeApp.service("CustomerService", function($http, $q){
	console.log("in customerService");

	var service = this;

	service.customer={
			id: -1,
			firstname: "",
			lastname: "",
			email : "",
			password : "",
			address: "",
			city: "",
			state: null,
			zipcode: "",
			phone: "",
			card: null,
			authenticated : false
	};

	service.getCustomer= function(){
		return service.customer;
	};

	service.resetCustomer = function(){
		service.customer={
				id: -1,
				firstname: "",
				lastname: "",
				email : "",
				password : "",
				address: "",
				city: "",
				state: null,
				zipcode: "",
				phone: "",
				card: null,
				authenticated : false
		};
	}

	service.setCustomer = function(data){
		service.customer.id         = data.id;
		service.customer.firstname  = data.firstname;
		service.customer.lastname   = data.lastname;
		service.customer.email      = data.email;
		service.customer.password   = data.password;
		service.customer.address    = data.address;
		service.customer.city       = data.city;
		service.customer.state      = data.state;
		service.customer.zipcode    = data.zipcode;
		service.customer.phone      = data.phone;
		service.customer.card       = data.card;
		service.customer.authenticated = data.authenticated;
	};

	service.authenticateUser = function(){
		var promise = $http.post(
				'rest/customer/auth', service.customer)
				.then(
						function(response){
							console.log(response.data);
							return response;
						},
						function(error){
							console.log('login promise fail');
						}
				);
		return promise;
	};
});

//Service that handles all things relating to Card
storeApp.service("CardService", function($http, $q){
	console.log("in cardService");

	var cardservice = this;

	cardservice.card={
			id: -1,
			cardnumber: "",
			nameoncard: "",
			expiration: "",
			securitycode: ""
	};

	cardservice.getCard= function(){
		return cardservice.card;
	};
	
	cardservice.setCard = function(data){
		cardservice.card.id           = data.id;
		cardservice.card.cardnumber   = data.cardnumber;
		cardservice.card.nameoncard   = data.nameoncard;
		cardservice.card.expiration   = data.expiration;
		cardservice.card.securitycode = data.securtiycode;
	};
	
});

// Takes in login information and handles creating a new Customer
storeApp.controller("LoginCtrl", function(CustomerService, $rootScope, $scope, $state, $http){
	console.log("in loginctrl");
	$scope.createCust = false;
	$scope.message = "";
	$scope.messageClass = "";
	
	var login = this;
	login.customer = CustomerService.getCustomer();

	login.doLogin = function(){
		console.log("about to authenticate user");
		var promise = CustomerService.authenticateUser();

		promise.then(
				function(response){
					if(response.data.id !== -1){
						console.log(response.data);
						CustomerService.setCustomer(response.data);
						$rootScope.authenticated = true;
						$rootScope.customer = response.data;
						$state.go("mainStorePage");
					} else {
						alert("Invalid login!");
					}
				},function(error){
					console.log(error);
				});

	};
	
	$http.get("rest/state/getAll").then(function(response){
		$scope.states = response.data;
		console.log($scope.states);
	})
	console.log($scope.states);
	
	$scope.createInfo = function() {
		var ncust = CustomerService.getCustomer();
			ncust.id = -1;
			ncust.firstname = $scope.custInfo.firstName;
			ncust.lastname = $scope.custInfo.lastName;
			ncust.email = $scope.custInfo.email;
			ncust.password = $scope.custInfo.password;
			ncust.address = $scope.custInfo.address;
			ncust.city = $scope.custInfo.city;
			ncust.state = $scope.chosenState;
			ncust.zipcode = $scope.custInfo.zipcode;
			ncust.phone = $scope.custInfo.phone;
			ncust.card = null;
			ncust.authenticated = false;
			
			$http.post('rest/customer/create', ncust).then(function(response){
				CustomerService.setCustomer(response.data);
				$rootScope.customer = response.data;
				$rootScope.customer.authenticated = true;
				$state.go('mainStorePage');
			});
	}
	
});


storeApp.controller('getOrdersCtrl',function($http,$rootScope, $scope, CustomerService){
	$scope.orders = [];
	
	$http.get('rest/order/getAllByCustomerId?id=' + $rootScope.customer.id).then( function(response){
		console.log($rootScope.customer.id);
		console.log(response.data);
		var orders = response.data;
		for(var i = 0; i < orders.length; i ++){
			var order = orders[i];
			var date = new Date(order.order_Date);
			order.order_Date = date.toLocaleDateString();
			order.show = false;
			order.lineitems = [];
			$scope.orders.push(order);
			$scope.loadOrder(order);
		}
		
	});

	$scope.loadOrder = function(order){
		$http.get('rest/lineitem/getAllByOrderId?id=' + order.id).then(function(response){
			console.log(response);
			order.lineitems = response.data;
			order.total = 0;
			order.lineitems.forEach(function(l){
				$http.get('rest/inventoryitem/get?id='+l.inventoryItem.id).then(function(response){
					l.inventoryitem = response.data;
					order.total += l.inventoryitem.unitPrice * l.quantity;
				});
			});
		});
	}
	
	

});

// Displays the Customer information and handles editing the customer information
storeApp.controller('custShowInfoController', function($http, $rootScope, $scope, CustomerService, CardService) {

	console.log("this is custshow");
	var customer = CustomerService.getCustomer();
	
	$scope.updateCustShow = false;
	
	$scope.custInfo = {
		firstName: customer.firstname,
		lastName: customer.lastname,
		email: customer.email,
		address: customer.address,
		city: customer.city,
		state: customer.state,
		zipcode: customer.zipcode,
		phone: customer.phone,
		card: customer.card
	}
	
	if($scope.custInfo.card != null) {
		$scope.cardlf = $scope.custInfo.card.cardnumber.substr($scope.custInfo.card.cardnumber.length - 4);
	}
	
	$http.get("rest/state/getAll").then(function(response){
		$scope.states = response.data;
	})
	
	$scope.updateInfo = function(){
		customer.firstname = $scope.custInfo.firstName;
		customer.lastname = $scope.custInfo.lastName;
		customer.email = $scope.custInfo.email;
		customer.city = $scope.custInfo.city;
		customer.state = $scope.chosenState;
		customer.zipcode = $scope.custInfo.zipcode;
		customer.phone = $scope.custInfo.phone;
		
		CustomerService.setCustomer(customer);
		
		$http.post('rest/customer/update', customer).then(function(response){
			$scope.message = "Info Updated."
			$scope.messageClass = "alert-success";
			$scope.updateCustShow = false;
		});
	};
	
	$scope.updatePass = function(){
		if($scope.pass1 != $scope.pass2){
			$scope.message = "New Passwords must match!";
			$scope.messageClass = "alert-danger";
		}
		else if($scope.currPass != customer.password){
			$scope.message = "Incorrect current password!";
			$scope.messageClass = "alert-danger";
		}
		else{
			customer.password = $scope.pass1;
			$http.post('rest/customer/update', customer).then(function(response){
				CustomerService.setCustomer(customer);
				$scope.passShow = false;
				$scope.message = "Password Updated."
				$scope.messageClass = "alert-success";
			});
		}
	};
	
	$scope.createCard = function() {
		var ncard = CardService.getCard();
		
		var dateStr = String($scope.nexpdate);
		var date = dateStr.split("/");
		var month = parseInt(date[0]);
		var year = parseInt(date[1]);
		
		if(String($scope.ncardnum).length !== 16) {
			$scope.cardmessage = "Card Number must contain 16 numbers!";
			$scope.cardmessageClass = "alert-danger";
		}
		else if($scope.ncardname == "") {
			$scope.cardmessage = "Card Name cannot be empty!";
			$scope.cardmessageClass = "alert-danger";
		}
		else if(date.length !== 2) {
			$scope.cardmessage = "Card Expiration must be MM/YY!";
			$scope.cardmessageClass = "alert-danger";
		}
		else if(month > 12 | month == 0) {
			$scope.cardmessage = "Card Expiration Month must be Between 01 - 12!";
			$scope.cardmessageClass = "alert-danger";
		}
		else if(year < 14) {
			$scope.cardmessage = "Card Expiration Year must be Greater than 14!";
			$scope.cardmessageClass = "alert-danger";
		}
		else if(String($scope.nscode).length < 3) {
			$scope.cardmessage = "Card Security Code must Contain more than 2 Numbers!";
			$scope.cardmessageClass = "alert-danger";
		}
		else {
			ncard.cardnumber   = $scope.ncardnum;
			ncard.nameoncard   = $scope.ncardname;
			ncard.expiration   = $scope.nexpdate;
			ncard.securitycode = $scope.nscode;
			
			$scope.cardlf = String(ncard.cardnumber).substr(String(ncard.cardnumber).length - 4);
			
			customer.card = {
					cardnumber: -1,
					nameoncard: "",
					expiration: "",
					securitycode: ""
			}
			
			customer.card.cardnumber   = ncard.cardnumber;
			customer.card.nameoncard   = ncard.nameoncard;
			customer.card.expiration   = ncard.expiration;
			customer.card.securitycode = ncard.securitycode;
			
			$http.post('rest/card/create', ncard).then(function(response){
				CardService.setCard(ncard);
				$http.post('rest/customer/update', customer).then(function(response){
					CustomerService.setCustomer(customer);
				})
				
				$scope.cardCustShow = false;
				$scope.cardmessage = "Card Created."
				$scope.cardmessageClass = "alert-success";
			});
		}
	};
	
	$scope.updateCard = function(){
		var dateStr = String($scope.expdate);
		var date = dateStr.split("/");
		var month = parseInt(date[0]);
		var year = parseInt(date[1]);
		
		if(String($scope.cardnum).length !== 16) {
			$scope.cardmessage = "Card Number must contain 16 numbers!";
			$scope.cardmessageClass = "alert-danger";
		}
		else if($scope.cardname == "") {
			$scope.cardmessage = "Card Name cannot be empty!";
			$scope.cardmessageClass = "alert-danger";
		}
		else if(date.length !== 2) {
			$scope.cardmessage = "Card Expiration must be MM/YY!";
			$scope.cardmessageClass = "alert-danger";
		}
		else if(month > 12 | month == 0) {
			$scope.cardmessage = "Card Expiration Month must be Between 01 - 12!";
			$scope.cardmessageClass = "alert-danger";
		}
		else if(year < 14) {
			$scope.cardmessage = "Card Expiration Year must be Greater than 14!";
			$scope.cardmessageClass = "alert-danger";
		}
		else if(String($scope.scode).length < 3) {
			$scope.cardmessage = "Card Security Code must Contain more than 2 Numbers!";
			$scope.cardmessageClass = "alert-danger";
		}
		else {
			customer.card.cardnumber = $scope.cardnum;
			customer.card.nameoncard = $scope.cardname;
			customer.card.expiration = $scope.expdate;
			customer.card.securitycode = $scope.scode;
			
			$scope.cardlf = String($scope.cardnum).substr(String($scope.cardnum).length - 4);
			
			$http.post('rest/customer/update', customer).then(function(response){
				CustomerService.setCustomer(customer);
				$scope.cardCustShow = false;
				$scope.cardmessage = "Card Info Updated."
				$scope.cardmessageClass = "alert-success";
			});
		}
	};
	
});

storeApp.service("ItemService", function($http, $q){
	var service = this;
	var current_item;
	service.setItem = function(item){
		current_item = item;
	};
	service.getItem = function(){
		return current_item;
	};
});

storeApp.controller('viewItemController', function($rootScope,$scope,$state,$http,CustomerService,ItemService,ItemsService){

	$scope.item = ItemService.getItem();
	$scope.inCartQuantity = ItemsService.getQuantityIfIn($scope.item);
	
	$scope.discountShow = false;
	$scope.showQuantityWarning = false;
	$scope.showReviewWarning = false;
	$scope.finishedReview = false;
	$scope.quantities = [];
	
	//set discount info
	if($scope.item.discountid != -1){
		$http.get('rest/discount/get?id='+$scope.item.discountid).then(function(response){
			console.log(response.data);
			if(response.data.discount_Type == 0){
				$scope.discountOffer = "$" + response.data.amount + " off!";
				$scope.discountMessage = response.data.description;
			} else{
				$scope.discountOffer = response.data.amount + "% off!";
				$scope.discountMessage = response.data.description;
			}
		}) 
		$scope.discountShow = true;
	}

	//set quantity list
	for(var i = 1; i <= $scope.item.quantity; i++){
		$scope.quantities.push(i);
	}


	//set product reviews info 
	$scope.productreviews = [];
	$scope.productreviewAvg = 0;

	$http.get('rest/productreview/getByItem?id='+$scope.item.id).then(function(response){
		$scope.productreviews = response.data;
		var sum = 0;
		for(var i = 0; i < $scope.productreviews.length; i++){
			sum = sum + $scope.productreviews[i].rating;
		}
		$scope.productreviewAvg = (sum / $scope.productreviews.length).toFixed(1);
		if($scope.productreviewAvg >= 0){
			$scope.roundedreviewAvg = Math.floor($scope.productreviewAvg);
		}
		else{
			$scope.productreviewAvg = 0;
		}
	});


	//submit product review
	$scope.submitReview = function(){
		if($scope.chosenRating == null){
			$scope.showReviewWarning = true;
			return;
		}

		var productreview = {
				id: -1,
				inventoryitemid: $scope.item.id,
				rating: $scope.chosenRating,
				description: $scope.userReviewDescription
		};
		
		console.log(productreview);
		$http.post('rest/productreview/create',productreview).then(function(response){
			$scope.productreviews.push(productreview);
			$scope.finishedReview = true;
		});
	};

	//line items
	
	//create and add line item to cart
	$scope.addToCart = function(item,quantity){
		if($scope.chosenQuantity == null){
			$scope.showQuantityWarning = true;
			return;
		}
		
		$scope.inCartQuantity = quantity;
		$rootScope.addItemToCart(item,quantity);
		item.inCart = true;
		console.log(ItemsService.getCart());
	};

	$scope.increaseCartQuantity = function(item,chosenQuantity){
		$scope.inCartQuantity = chosenQuantity;
		if(chosenQuantity == 0){
			ItemsService.removeFromCart(item);
		}else{
			ItemsService.updateItemQuantity(item,chosenQuantity);
		}
	};
	
	console.log($scope.item);

});


storeApp.controller('viewCartController', function($rootScope,$scope,$state,$http,$timeout,CustomerService,ItemService,ItemsService){
	$scope.cart = ItemsService.getCart();
	$rootScope.customer = CustomerService.getCustomer();
	$scope.showFirstButtons = true;
	$scope.showProcessing = false;
	$scope.cart.total = 0;
	$scope.progress = 20;
	$scope.date = new Date().toLocaleDateString()
	
	$scope.cart.forEach(function(i){
		if(i.removed == true){
			ItemsService.removeFromCart(i);
			return; //next loop iteration
		}
		$scope.cart.total += i.unitPrice * i.quantity;
		i.removed = false;
	});	
	
	$scope.removeFromCart = function(item){
		item.removed = true;
		item.inCart = false;
		$scope.cart.total = 0;
		$scope.cart.forEach(function(i){
			if(!i.removed){
				$scope.cart.total += i.unitPrice * i.quantity;
			}	
		});	
	};
	
	$scope.addItemBack = function(item){
		item.removed = false;
		item.inCart = true;
		$scope.cart.total = 0;
		$scope.cart.forEach(function(i){
			if(!i.removed){
				$scope.cart.total += i.unitPrice * i.quantity;
			}	
		});	
	};
	
	$scope.checkout = function() {
		$scope.showFirstButtons = false;
		console.log($rootScope.customer);
		if ($rootScope.customer.id < 0) {
			$scope.showLogin = true;
			$scope.progress = 40;
		} else {
			if($scope.customer.card == null){
				$scope.customer.card = {};
				$scope.showPaymentForm = true;
				$scope.progress = 60;
			}
			else{
				$scope.showPayment = true;
				$scope.showCheckout = true;
				$scope.progress = 80;
			}
		}
	};
	
	$scope.login = function(){
		$rootScope.customer.email = $scope.inputEmail;
		$rootScope.customer.password = $scope.inputPass;
		
		$http.post('rest/customer/auth',$scope.customer).then(function(response){
			console.log(response.data);
			if(response.data.authenticated){
				$rootScope.customer = response.data;
				CustomerService.setCustomer($scope.customer);
				$scope.showLogin = false;
				$scope.showFailLogin = false;
				if($scope.customer.card == null){
					$scope.customer.card = {};
					$scope.showPaymentForm = true;
					$scope.progress = 60;
				}
				else{
					$scope.showPayment = true;
					$scope.showCheckout = true;
					$scope.progress = 80;
				}
			}else{
				$scope.showFailLogin = true;
			}
		});
	};
	
	$scope.submitPaymentInfo = function(){
		$rootScope.customer.card.id = -1;
		console.log($rootScope.customer.card);
		$scope.showPaymentForm = false;
		$http.post('rest/card/create',$rootScope.customer.card).then(function(response){
			$rootScope.customer.card = response.data;
			CustomerService.setCustomer($rootScope.customer);
			$http.post('rest/customer/update',$rootScope.customer).then(function(response2){
				$scope.showPayment = true;
				$scope.showCheckout = true;
				$scope.progress = 90;
			});
		});
		
	};
	
	$scope.processOrder = function(){
		$scope.showProcessing = true;
		$scope.progress = 100;
		let newOrder = {
				id: -1,
				customer: $rootScope.customer,
				order_Date: new Date().getTime()
		}
		ItemsService.createOrder(newOrder).then(function(response) {
			console.log(response.data);
			newOrder = response.data;
			ItemsService.setCart = $scope.cart;
			$scope.cart.forEach(function(item){
				if(item.removed != true){
					ItemsService.createLineItem(response.data,item);
				}
			});
		});
		ItemsService.emptyCart();
		
		$timeout( function(){ 
				$http.post('rest/order/sendEmail',newOrder);
				$state.go('getPastOrders');	
			} , 5000);
	};
});








