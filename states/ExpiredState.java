package states;

import models.Member;
import models.Membership;

public class ExpiredState implements MembershipState {
    @Override
    public void handleCheckIn(Member member, Membership context) {
        System.out.println("❌ Access Denied! " + member.getName() + ", your membership has expired.");
    }
}