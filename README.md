# REST Assured Api Tests
>*libraries*  REST Assured , TestNG  

## Github
**1 - Get User Information**
  
| Request Url  | Request Type |
| ------------- | ------------- |
| https://api.github.com/user  | GET  |

</br>

**Method :**
</br>

    @Test
    void getUser(){
    
        String requestUrl = baseUrl + "/user";

        Response response =
        given().
                headers("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                get(requestUrl).
        then().
                statusCode(200).
                time(lessThan(3000L)).
                extract().response();

        String name = response.path("name").toString();
        String email = response.path("email").toString();
        String id = response.path("id").toString();

        Assert.assertEquals(name,"Mustafa Selim Gunaydin");
        Assert.assertEquals(email,"mustafaselimgunaydin@gmail.com");
        Assert.assertEquals(id,"88919177");
    }
    
![api1](https://user-images.githubusercontent.com/88919177/147881833-353f6185-9282-4a04-80cb-43a013ce5b67.gif)

</br>

**2 - Get Repositories**

| Request Url  | Request Type |
| ------------- | ------------- |
| https://api.github.com/users/{{username}}/repos | GET  |

</br>

**Method :**
</br>

    @Test
    void getRepos(){
    
        String requestUrl = baseUrl + "/users/" + username + "/repos";

        Response response =
        given().
                headers("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                get(requestUrl).
        then().
                statusCode(200).
                time(lessThan(3000L)).
                extract().response();

        int i = 0;
        while (true){
            String repo = response.path("["+i+"].name");
            String name = response.path("["+i+"].owner.login");
            i++;
            if (repo != null){
                Assert.assertEquals(name,"MustafaSelimG");
                System.out.println( i + " - " + repo);
            }
            else break;
        }
    }

![api2](https://user-images.githubusercontent.com/88919177/147882344-bc0bbe35-624b-4529-84e6-66c251523000.gif)

</br>

**3 - Get Repository**

| Request Url  | Request Type |
| ------------- | ------------- |
| https://api.github.com/repos/{{username}}/{{repo}} | GET  |

</br>

**Method :**
</br>

    @Test
    void getRepo(){
    
        String repoName = "MustafaSelimG";
        String requestUrl = baseUrl + "/repos/" + username + "/" + repoName;

        Response response =
        given().
                headers("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                get(requestUrl).
        then().
                statusCode(200).
                time(lessThan(3000L)).
                extract().response();

        String id = response.path("id").toString();
        String name = response.path("name").toString();
        String admin = response.path("permissions.admin").toString();

        Assert.assertEquals(id,"410197376");
        Assert.assertEquals(name,"MustafaSelimG");
        Assert.assertEquals(admin,"true");
    }
    
  ![api3](https://user-images.githubusercontent.com/88919177/147882499-38854d7f-906b-4fb7-b3d1-ebc2ba875ef8.gif)
  
  </br>
  
**4 - Get Readme File**

| Request Url  | Request Type |
| ------------- | ------------- |
| https://api.github.com/repos/{{username}}/{{repo}}/readme | GET  |
</br>

**Method :**</br>

    @Test
    void getReadme(){
    
        String repoName = "MustafaSelimG";
        String requestUrl = baseUrl + "/repos/" + username + "/" + repoName + "/readme";

        Response response =
        given().
                headers("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                get(requestUrl).
        then().
                statusCode(200).
                time(lessThan(3000L)).
                extract().response();

        String path = response.path("path");
        Assert.assertEquals(path,"README.md");
    }
    
![api4](https://user-images.githubusercontent.com/88919177/147882575-cd7d2b1d-828a-476e-866b-d72d6d6a2aca.gif)

</br>
    
**5 - Post Repository**

| Request Url  | Request Type |
| ------------- | ------------- |
| https://api.github.com/user/repos | POST  |

</br>

**Method :**</br> 

    @Test
    void postRepo(){
    
        String requestUrl = baseUrl + "/user/repos";
        String name = RandomStringUtils.randomAlphabetic(5);

        JSONObject request = new JSONObject();
        request.put("name",name);
        request.put("auto_init","true");
        request.put("private","true");
        request.put("gitignore_template","nanoc");

        Response response =
        given().
                headers("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                body(request.toJSONString()).
        when().
                post(requestUrl).
        then().
                statusCode(201).
                time(lessThan(3000L)).
                extract().response();

        String repoName = response.path("name").toString();
        Assert.assertEquals(repoName,name);
    }
    
![api5](https://user-images.githubusercontent.com/88919177/147882806-3dae9a06-122f-4790-8ced-18a8b88bf8c8.gif)
    
</br>

**6 - Delete Repository**

| Request Url  | Request Type |
| ------------- | ------------- |
| https://api.github.com/repos/{{username}}/{{repo}} | DELETE  |

</br>

**Method :**</br>

    @Test
    void deleteRepo(){
    
        String repoName = "bxVVP";
        String requestUrl = baseUrl + "/repos/" + username + "/" + repoName;

        given().
                headers("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
                delete(requestUrl).
        then().
                statusCode(204).
                time(lessThan(3000L));
    }

![api6](https://user-images.githubusercontent.com/88919177/147882840-1f0ee8a1-892a-44ca-b53b-b5bc5dc2935f.gif)

</br>


## Medium

**1 - Get User Information**
  
| Request Url  | Request Type |
| ------------- | ------------- |
| https://api.medium.com/v1/me  | GET  |

</br>

**Method :**

    @Test
    void getUser(){
        String requestUrl = baseUrl + "/v1/me";

        Response response =
        given().
                headers("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
        when().
                get(requestUrl).
        then().
                statusCode(200).
                time(lessThan(3000L)).
                extract().response();

        String responseName = response.path("data.name");
        String responseUsername = response.path("data.username");

        Assert.assertEquals(responseName,name);
        Assert.assertEquals(responseUsername,username);
    }
    
 ![api-1](https://user-images.githubusercontent.com/88919177/147883482-ddbb5efd-d545-4b83-83b5-79d1f75310ae.gif)
 
 </br>
   
 **2 - Get Publications**
  
| Request Url  | Request Type |
| ------------- | ------------- |
| https://api.medium.com/v1/users/{{userId}}/publications  | GET  |

</br>

**Method :**  

    @Test
    void getPublications(){
        String requestUrl = baseUrl + "/v1/users/" + userId + "/publications";

        Response response =
        given().
                headers("Authorization", "Bearer " + token).
                contentType(ContentType.JSON).
                accept(ContentType.JSON).
        when().
                get(requestUrl).
        then().
                statusCode(200).
                time(lessThan(3000L)).
                extract().response();

        int i=0;
        while (true){
            String publicationName = response.path("data["+i+"].name");
            String publicationId = response.path("data["+i+"].id");
            i++;
            if (publicationName != null){
                System.out.println(i + " - " + publicationName + " - " + publicationId);
            }
            else break;
        }
    }
    
  ![api-2](https://user-images.githubusercontent.com/88919177/147883534-b32d98df-4f8b-4fb8-b1f4-8a42497a5d29.gif)
  
  </br>
  
   **3 - Create a post**
  
| Request Url  | Request Type |
| ------------- | ------------- |
| https://api.medium.com/v1/users/{{userId}}/posts  | POST  |

</br>

**Method :**  
  
    @Test
    void createPost(){
        String requestUrl = baseUrl + "/v1/users/" + userId + "/posts";

        JSONObject request = new JSONObject();

        request.put("title","Api Test");
        request.put("contentFormat","html");
        request.put("content","<h1> Api Test with Medium </h1><p> Rest Assured api test.</p>");

        Response response =
                given().
                        headers("Authorization", "Bearer " + token).
                        contentType(ContentType.JSON).
                        accept(ContentType.JSON).
                        body(request.toJSONString()).
                when().
                        post(requestUrl).
                then().
                        statusCode(201).
                        time(lessThan(3000L)).
                        extract().response();

        String postTitle = response.path("data.title").toString();
        String authorId = response.path("data.authorId").toString();

        Assert.assertEquals(postTitle,"Api Test");
        Assert.assertEquals(authorId,userId);
    }
    
![api-3](https://user-images.githubusercontent.com/88919177/147883579-f1e77dd3-0c57-480b-afbf-c9455d2db628.gif)


