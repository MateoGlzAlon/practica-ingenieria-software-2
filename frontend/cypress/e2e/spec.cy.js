describe('stoa e2e', () => {

  it('layoutExists', () => {
    cy.visit('http://localhost:3000');
    cy.get('#navbar-title').should('exist');
    cy.get('#navbar-title').contains('Stoa');

  })

  it('visitPost', () => {
    cy.visit('http://localhost:3000');
    cy.get('.px-16 > :nth-child(2)').click();
    cy.url().should('include', '/post/');
    cy.get('#post-title').should('exist');
    cy.get('#post-title').contains('Intro to Python');
  })

  it('visitPostComment', () => {
    cy.visit('http://localhost:3000');
    cy.get('.px-16 > :nth-child(2)').click();
    cy.url().should('include', '/post/');
    cy.get('#answers-title').should('exist');
    cy.get('#answers-title').contains('Answers');
  })

})