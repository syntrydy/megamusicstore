@ignore
Feature: Sign in

Scenario: Sign in as admin
 	When 	I sign is with user name 'admin' and password 'admin'
 	Then 	I should be redirect to 'admin' page
 	
Scenario: Sign in as user
 	When 	I sign is with user name 'user' and password 'user'
 	Then 	I should be redirect to 'welcome' page