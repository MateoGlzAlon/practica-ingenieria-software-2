import Post from "./Post";
import { mockData } from "@/app/mockData";

export default function MainFeed() {


    return (
        <div className="flex-1 flex flex-col gap-4 px-4  border-r-2 border-black">
            <h1 className="text-xl font-bold text-center">For you page</h1>

            {mockData.postExamples.map((post) => (
                <Post key={post.id} postData={post} />
            ))}
        </div>
    );
}
