const renderDiv = document.getElementById('posts');
const url = 'localhost:8080/posts.json';
const getPosts = () => fetch(url)
    .then(res => res.json())
    .then(posts => {
        console.log(posts);
        posts.forEach(post => renderPost(post))
    })
    .catch(err => console.log(err));

const renderPost = (post) => {
    let output = `
    <div>
        <h1>${post.title}</h1>
        <p>${post.body}</p>
        <p>published by ${post.user.userName}</p>
    </div>
    `;
    renderDiv.innerHTML += output;
}