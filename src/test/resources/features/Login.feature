Feature: Login user

  Background:
    Given User opens application URL
    And navigates om Login page

  Scenario Outline: Login with valid credentials
    When User enters valid email <email>
    And Enters valid password <password>
    And Click on login button
    Then User login successfully
    Examples:
      | email                    | password |
      | "amotooricap9@gmail.com" | "12345"  |
      | "amotooricap3@gmail.com" | "12345"  |

  Scenario Outline: Login with invalid credentials
    When User enters valid email <email>
    And Enters valid password <password>
    And Click on login button
    Then User should get a warning message
    Examples:
      | email                        | password |
      | "amotooricap93433@gmail.com" | "123456" |
      | "amotooricap934@gmail.com"   | "123456" |
      | "amotooricap9@gmail.com"     | "12345"  |


  Scenario Outline: Login with valid email and invalid password

    When User enters valid email <email>
    And Enters invalid password <password>
    And Click on login button
    Then User should get a warning message
    Examples:
      | email                    | password |
      | "amotooricap9@gmail.com" | "123456" |

  Scenario Outline: Login with invalid email and valid password

    When User enters invalid email <email>
    And Enters valid password <password>
    And Click on login button
    Then User should get a warning message
    Examples:
      | email                       | password |
      | "amotooricap9343@gmail.com" | "12345"  |




