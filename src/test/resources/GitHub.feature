Feature: GitHub Test Cases

  Scenario: send get request for user and verify response body
    Given send get request for user
    Then verify status code for success
    And verify response body for get user

  Scenario: send get request for repositories and verify response body
    Given send get request for repositories
    Then verify status code for success
    And verify response body for get repositories

  Scenario: send get request for repository and verify response body
    Given send get request for repository
    Then verify status code for success
    And verify response body for get repository

  Scenario: send get request for readme and verify response body
    Given send get request for readme
    Then verify status code for success
    And verify response body for get readme

  Scenario: send post request for repository and verify response body
    Given send post request for repository
    Then verify status code for created
    And verify response body for post repository

  Scenario: send delete request for repository and verify response body
    Given send delete request for repository
    Then verify status code for no content