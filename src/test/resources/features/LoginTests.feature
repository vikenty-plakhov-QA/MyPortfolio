Feature: login tests
  Background:
   Given I open browser
    When I open login page
  Scenario Outline: login success

    And  i enter valid email <email>
    And I enter valid password <password>
    And  I click Submit
    Then I am logged in
   Examples:
    |email                  |   password|
    |"vikenty.plakhov@testpro.io"|"MEGAdelta06"|


  Scenario Outline:LoginWrongEmail

    And I enter wrong email
    And I enter valid password <password>
    And I click Submit
    Then I am not logged in
    Examples:
      |   password|
      |"MEGAdelta06|

 ## Scenario  : login1 success
#    And  I enter details below into fields
 #     |email                        |password|
   #   | vicplach123@gmail.com    |  MEGAdelta123@   |
#    And  i enter valid email <email>
 #   And I enter valid password <password>
 #   And  I click Submit
  #  Then I am logged in
  #  Examples:
   #   |email                  |   password|
    #  |"vicplach123@gmail.com"|"MEGAdelta123@"|
