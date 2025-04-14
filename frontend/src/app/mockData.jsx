export const mockData = {
    postExamples: [
        {
            id: 1,
            title: "Need help with programming project",
            image: "https://fakeimg.pl/600x400?text=image_post1",
            state: "Open",
            upvotes: 12,
            downvotes: 2,
            state: "Open",
        },
        {
            id: 2,
            title: "What's the best JavaScript framework in 2025?",
            image: "https://fakeimg.pl/600x400?text=image_post2",
            state: "Discussion",
            upvotes: 34,
            downvotes: 5,
            state: "Solved",
        },

    ],

    postSideBarTags: ["Home", "Questions", "Tags"],

    commentsExamples: [
        {
            id: 1,
            content: "Another approach is to use Grid layout with the `place-items-center` utility.",
            votes: 37,
            accepted: false
        },
        {
            id: 2,
            content: "If you prefer absolute positioning, you can do it this way, with `absolute`, `top-1/2`, `left-1/2`, and `transform`.",
            votes: 12,
            accepted: true
        },
        {
            id: 0,
            content: "To center a div both horizontally and vertically with Tailwind CSS, you can use Flexbox.",
            votes: 125,
            accepted: false
        }
    ],

    postExampleDetails: {
        question: {
            id: 1,
            title: "How to center a div horizontally and vertically using Tailwind CSS?",
            content: "I'm trying to center a div horizontally and vertically using Tailwind CSS, but I'm having trouble getting it to work correctly. Here's my current code:",
            votes: 42
        },
        codeExample:
            `<div className="h-screen">
    <div className="bg-blue-500 p-4 text-white">
        This div should be centered
    </div>
</div>`
    }


};
