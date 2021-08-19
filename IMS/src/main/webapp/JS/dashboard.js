adminApp.factory("LoadChart1", function($http, $q) {
	
	return function() {
		var deferedobject = $q.defer();
		$http.get('rest/lineitem/soldByDate').success(function(data) {
			deferedobject.resolve({ data : data });
		}).error(function() {
			deferedobject.resolve({ data : false });
		});
		return deferedobject.promise;
	}
});


adminApp.directive("chart1", function(LoadChart1) {
	return {
		restrict: 'A',
		link: function ($scope, $elm, $attr) {
			var data = new google.visualization.DataTable();
			data.addColumn('string', 'Date');
			data.addColumn('number', 'Sold');
			
			var result = LoadChart1();
			result.then(function(output) {
				angular.forEach(output.data, function(value, key) {
					data.addRow([value[0], value[1]]);
				});

				var options = {
					"title": "Items Sold by Date",
					"width": 500,
					"height": 400,
					"legend": {position:'none'},
					"hAxis": {textPosition: 'none' , title:"Time"},
					"vAxis": {title:"# of items"}
					
				}
			
				var chart = new google.visualization.LineChart($elm[0]);
				chart.draw(data, options);
			});
		}
	}
});

adminApp.factory("LoadChart2", function($http, $q) {
	
	return function() {
		var deferedobject = $q.defer();
		$http.get('rest/lineitem/soldByDept').success(function(data) {
			deferedobject.resolve({ data : data });
		}).error(function() {
			deferedobject.resolve({ data : false });
		});
		return deferedobject.promise;
	}
});


adminApp.directive("chart2", function(LoadChart2) {
	return {
		restrict: 'A',
		link: function ($scope, $elm, $attr) {
			var data = new google.visualization.DataTable();
			data.addColumn('string', 'Department');
			data.addColumn('number', 'Count');
			
			var result = LoadChart2();
			result.then(function(output) {
				angular.forEach(output.data, function(value, key) {
					data.addRow([value[0], value[1]]);
				});

				var options = {
					"title": "Items Sold by Department",
					"width": 500,
					"height": 400
				}
			
				var chart = new google.visualization.PieChart($elm[0]);
				chart.draw(data, options);
			});
		}
	}
});

adminApp.factory("LoadChart3", function($http, $q) {
	
	return function() {
		var deferedobject = $q.defer();
		$http.get('rest/inventoryitem/countByDept').success(function(data) {
			deferedobject.resolve({ data : data });
		}).error(function() {
			deferedobject.resolve({ data : false });
		});
		return deferedobject.promise;
	}
});


adminApp.directive("chart3", function(LoadChart3) {
	return {
		restrict: 'A',
		link: function ($scope, $elm, $attr) {
			var data = new google.visualization.DataTable();
			data.addColumn('string', 'Department');
			data.addColumn('number', 'Count');
			
			var result = LoadChart3();
			result.then(function(output) {
				angular.forEach(output.data, function(value, key) {
					data.addRow([value[0], value[1]]);
				});

				var options = {
					"title": "Inventory Items by Department",
					"width": 500,
					"height": 400
				}
			
				var chart = new google.visualization.PieChart($elm[0]);
				chart.draw(data, options);
			});
		}
	}
});

adminApp.factory("LoadChart4", function($http, $q) {
	
	return function() {
		var deferedobject = $q.defer();
		$http.get('rest/inventoryitem/countDiscountsByDept').success(function(data) {
			deferedobject.resolve({ data : data });
		}).error(function() {
			deferedobject.resolve({ data : false });
		});
		return deferedobject.promise;
	}
});


adminApp.directive("chart4", function(LoadChart4) {
	return {
		restrict: 'A',
		link: function ($scope, $elm, $attr) {
			var data = new google.visualization.DataTable();
			data.addColumn('string', 'Department');
			data.addColumn('number', 'Count');
			
			var result = LoadChart4();
			result.then(function(output) {
				angular.forEach(output.data, function(value, key) {
					data.addRow([value[0], value[1]]);
				});

				var options = {
					"title": "Discounted Items by Department",
					"width": 500,
					"height": 400,
					"pieHole": 0.4
				}
			
				var chart = new google.visualization.PieChart($elm[0]);
				chart.draw(data, options);
			});
		}
	}
});

google.load('visualization', '1', {packages:['corechart']} );
// Set a callback to run when the Google Visualization API is loaded.
//google.setOnLoadCallback(chart);
