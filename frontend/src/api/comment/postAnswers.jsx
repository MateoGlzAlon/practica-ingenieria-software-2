import axios from 'axios';
import { DATA } from "@/app/data";

export default async function postAnswer({ postId, userId, content }) {
    try {

        const bodyData = {
            userId: userId,
            postId: postId,
            content: content
        }

        //console.log("Body data = ", bodyData);

        const response = await axios.post(`${DATA.apiURL}/comments`, 
            bodyData
        );

        return response.data;
    } catch (error) {
        console.error("Error posting comment:", error);
        throw error;
    }
}