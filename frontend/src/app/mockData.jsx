export const mockData = {
    //landingPage mockData DONE     <---- /posts/landingPageFeed
    postExamples: [
        {
            id: 1,
            title: "Need help with programming project",
            image: "https://fakeimg.pl/600x400?text=image_post1",
            state: "Open",
            upvotes: 12,
            downvotes: 2,
        },
        {
            id: 2,
            title: "What's the best JavaScript framework in 2025?",
            image: "https://fakeimg.pl/600x400?text=image_post2",
            upvotes: 34,
            downvotes: 5,
            state: "Solved",
        },

    ],

    //done      <--- /tags/all
    postSideBarTags: ["javascript", "react", "node.js", "css", "python"],

    //individual comments   <---   /comments/post/{id}
    commentsExamples: [
        {
            id: 1,
            author: "alice",
            authorProfilePicture: "https://placehold.co/600x400?text=User1",
            createdAt: "19-4-2025",
            commentCount: 2,
            content: "Another approach is to use Grid layout with the `place-items-center` utility.",
            votes: 37,
            accepted: false
        },
        {
            id: 2,
            author: "bob",
            authorProfilePicture: "https://placehold.co/600x400?text=User2",
            createdAt: "19-4-2025",
            commentCount: 2,
            content: "If you prefer absolute positioning, you can do it this way, with `absolute`, `top-1/2`, `left-1/2`, and `transform`.",
            votes: 12,
            accepted: true
        },
        {
            id: 3,
            author: "charlie",
            authorProfilePicture: "https://placehold.co/600x400?text=User3",
            createdAt: "19-4-2025",
            commentCount: 2,
            content: "To center a div both horizontally and vertically with Tailwind CSS, you can use Flexbox.",
            votes: 125,
            accepted: false
        }
    ],


    //individual initial block      <--- /posts/details/{id}
    postExampleDetails: {
        id: 1,
        author: "Percy",
        authorProfilePicture: "https://placehold.co/600x400?text=User1",
        postImages: [
            "https://fakeimg.pl/600x400?text=image_post1",
            "https://fakeimg.pl/600x400?text=image_post2",
        ],
        title: "How to center a div horizontally and vertically using Tailwind CSS?",
        content: `I'm trying to center a div horizontally and vertically using Tailwind CSS, but I'm having trouble getting it to work correctly. Here's my current code:

\`\`\`jsx
<div className="h-screen">
  <div className="bg-blue-500 p-4 text-white">
    This div should be centered
  </div>
</div>
\`\`\`
`,

        votes: 42,
        createdAt: "17-4-2025",
    },

    //done          <--- /users/profile/{id}
    profilePageExample: {
        user: {
            name: "UPDATE-DB",
            username: "alice",
            avatar: "https://placehold.co/400x400?text=User1",
            role: "UPDATE-DB-IF-WANTED",
            memberSince: "April 2021",
            stats: {
                questions: 42,
                answers: 187,
            },
            about:
                "Developer with over 8 years of experience in web technologies. Specialized in React, Node.js, and cloud architectures. Active contributor to open source projects and mentor to junior developers.",
            links: {
                github: "github.com/carlos_dev",
                twitter: "twitter.com/carlos_dev",
                website: "carlosdev.com",
            },
            tipsSent: [
                {
                    receiver: "john",
                    amount: 7,
                    date: "2025-03-15",
                    postId: 1,
                    commentId: 1,
                },
                {
                    receiver: "emma",
                    amount: 12,
                    date: "2025-04-02",
                    postId: 1,
                    commentId: 2,
                },
            ],
            tipsReceived: [
                {
                    sender: "bob",
                    amount: 10,
                    date: "2025-03-12",
                    postId: 2,
                    commentId: 3,
                },
                {
                    sender: "alice",
                    amount: 5,
                    date: "2025-04-01",
                    postId: 2,
                    commentId: 4,
                },
            ],

        },
        activityData: [
            { month: "Jan", contributions: 45 },
            { month: "Feb", contributions: 32 },
            { month: "Mar", contributions: 67 },
            { month: "Apr", contributions: 58 },
            { month: "May", contributions: 43 },
            { month: "Jun", contributions: 89 },
            { month: "Jul", contributions: 76 },
            { month: "Aug", contributions: 91 },
            { month: "Sep", contributions: 65 },
            { month: "Oct", contributions: 83 },
            { month: "Nov", contributions: 72 },
            { month: "Dec", contributions: 56 },
        ],
        posts: [
            {
                id: 1,
                title: "How to optimize performance in React applications",
                votes: 124,
                answers: 8,
                date: "published on 15-3-2023",
            },
            {
                id: 2,
                title: "Complete guide to implementing JWT authentication",
                votes: 98,
                answers: 12,
                date: "published on 22-5-2023",
            },
            {
                id: 3,
                title: "Best practices for structuring Node.js projects",
                votes: 87,
                answers: 6,
                date: "published on 7-8-2023",
            },
        ],


    }



};
