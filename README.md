# spring-security-example simple description 

## branch basic-auth

Implementação simples do basic auth do spring 
```
  const header = new Headers();
  header.set('Authorization',`Basic ${btoa('admin:admin')}`)
  const request = new Request('http://localhost:8080/home',{
    method: 'GET',
    headers: header,
    mode: 'cors',
    cache: 'default'
  });
  function makeRequest(){
    fetch(request).then((response)=>{
      response.json().then(data => console.log(data));
    });
  }
```
