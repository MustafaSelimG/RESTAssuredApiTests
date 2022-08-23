Feature: Medium Test Cases

  Scenario: send get request for user profile and verify response body
    Given send get request for user profile
    Then assert status code for success
    And assert response body for get user profile

  Scenario: send get request for publications and verify response body
    Given send get request for publications
    Then assert status code for success
    And assert response body for get publications

  Scenario: send post request for create post and verify response body
    Given send post request for create post
    Then assert status code for created
    And assert response body for create post