import axios from 'axios';
import { DATA } from "@/app/data";

export default async function createCommentVote({ userId, commentId }) {
    try {

        const bodyData = {
            userId: userId,
            commentId: commentId
        }

        //console.log("BODY DATA = ", bodyData)

        const response = await axios.post(`${DATA.apiURL}/commentvotes`, 
            bodyData
        );

        return response.data;
    } catch (error) {
        console.error("Error upvoting comment:", error);
        throw error;
    }
}