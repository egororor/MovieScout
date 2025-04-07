For the AI generations, I mainly used chatGPT o3-mini-high as it is a reasoning model that is good at understanding code problems.
The AI implemented the sharing logic and its access to the user interface (as a user, one can send the title and description of a movie to
a friend by using the platform-specific sharing interface) for the app from scratch. Bridging was addressed through iterative, 
prompt refinement, the model had issues with using correct dependencies for platform specific and common code spaces. Also, 
I only had a boilerplate UI for the detailed movie page. But I wanted a fancy UI, so I found an example on the internet that I liked and
asked the AI to create a UI like the one in the photo. It worked pretty well, I just had to change it a bit. The rest of the app was 
written manually or with the help of the AI to solve some problems or understand some concepts, but no pure code generation.