# GitHubSearchApp

- GitHubSearchApp is an android application where users can search for repositories on Github by keywords
as well as any qualifiers using the v4 GraphQL API and see the results in a list.


<img src="https://github.com/tongtongi/GitHubSearch/blob/master/Gif.gif" width="350" height="700"/>

### How To Use App

- Before you start, you should add your own token value(TOKEN) i.e '"3d4......2ce"' into app level build.gradle file.

- In search screen, You can enter a query i.e. <b>language:python</b> to see the repositories whose languages are python, 
<b>forks:100</b> to see the ones who has forks over hundred or <b>stars:>5000</b> to see the ones who has stars over 
five thousand or <b>codility in:description</b> to find the repositories which has codility keyword in their descriptions.
(More queries: <b>tongtongi in:name</b>, <b>codility in:readme</b>, <b>codility in:description tongtongi in:name</b>).

- When find button is clicked, you will be directed to a screen where you can see all the repo search results as a list.
If you click any repo item, you will see the details of that repo.

### Architecture and Implementation Details
- This app is implemented 100% in Kotlin
- MVVM Architecture, LiveData, ViewModel from Architecture Components
- GraphQL, Apollo, RxJava
- Dagger2 for dependency injection

- More info about Github v4 API: https://developer.github.com/v4/
- How to create access token: https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token
