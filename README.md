# Social Media Application Backend
A secured backend for social media application

# Tech Stack
  - Spring
  - Spring Security
  - Spring Data JPA
  - JUnit
# Endpoints

  PREFIXES: 
  - /user - prefix to all user endpoints
  - /post - prefix to all post endpoints

  ADMIN role authorization endpoints:
    - /user/all fetches all the users
    - /post/all fetches all the posts
    
  ADMIN and USER role authorization endpoints:
    - /user/update/email changes the email with the provided one
    - /user/update/password changes the password with the provided one
    - /post/add creates a post
    
  non-authorizated endpoints:
    - /user/add creates a user 
  
  
  
  
