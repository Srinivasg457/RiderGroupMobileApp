Feature: Rider Group App launch and MAKE AN ORDER

  Scenario: Launch the application and order The product
    Given  uer navigates to chataak application
#       Then Enter with the mobile number and the otp
#     And Performs an Operation to Order an product

  Scenario: Launch the application and post an image
    Given  uer navigates to chataak application
    When user navigates to post creation screen
    And user selects an image from gallery
    And user adds a caption to the post
    And user posts the image
    Then the post should be created successfully
