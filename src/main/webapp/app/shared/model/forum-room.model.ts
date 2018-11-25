export interface IForumRoom {
    id?: number;
    forumRoomName?: string;
}

export class ForumRoom implements IForumRoom {
    constructor(public id?: number, public forumRoomName?: string) {}
}
