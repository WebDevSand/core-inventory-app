
var app = angular.module('indexApp', ['ui.router']);

app.config(function($stateProvider, $urlRouterProvider) {
	$urlRouterProvider.otherwise("/storefront/mainStorePage");
	
	$stateProvider
	.state("storefront.mainStorePage", {
		url: "/mainStorePage",
		templateUrl: "partials/mainStorePage.html"
	})
	
	.state("homePage", {
		url: "/homePage",
		templateUrl: "partials/homePage.html"
	});
});