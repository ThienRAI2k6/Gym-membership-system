package states;

import models.Member;
import models.Membership;

public class ActiveState implements MembershipState {
    @Override
    public void handleCheckIn(Member member, Membership context) {
        System.out.println("✅ Access Granted! Welcome to the gym, " + member.getName());
    }
}