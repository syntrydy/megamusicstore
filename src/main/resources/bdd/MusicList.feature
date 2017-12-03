Feature: Show music list as administartor

Scenario: Show music list
 	When 	I sign in with user name 'admin' and password 'admin'
 	Then 	I should be redirect to 'admin' page
 	And 	I show the music list
 	Then 	I should see a music titled 'Run THE WORLD' sung by 'Beyonce'
 
Scenario: Delete music from list
 	When 	I sign in with user name 'admin' and password 'admin'
 	Then 	I should be redirect to 'admin' page
 	When 	I show the music list
 	Then 	I should see a music titled 'Run THE WORLD' sung by 'Beyonce'
 	#When 	I delete the current music
 	#Then 	The music 'Run THE WORLD' will no longer be visible on music list 	
 	
 	
