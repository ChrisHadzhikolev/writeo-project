describe('The Home Page', () => {
    it('successfully loads', () => {
      cy.visit('http://localhost:3000')
    })
})

describe('Go to Login', () => {
    it('successfully clicks login', () => {
        cy.visit('http://localhost:3000')

        cy.get('#login_link').click()
    })
})

describe('Go to Register', () => {
    it('successfully clicks register', () => {
        cy.visit('http://localhost:3000')

        cy.get('#register_link').click()
    })
})

describe('Register', () => {
    it('successfully registers', () => {
        cy.visit('http://localhost:3000/register')

        cy.get('#username')
            .type('aziska')
        cy.get('#email')
            .type('azisepazise@gmail.com')
        cy.get('#password')
            .type('12345678')
        cy.get('#cpassword')
            .type('12345678')

        cy.get('#createBtn').click()
    })
})

describe('Successful Login', () => {
    it('successfully logins', () => {
        cy.visit('http://localhost:3000/login')

        cy.get('#username')
            .type('aziska')
            .should('have.value', 'aziska')
        cy.get('#password')
            .type('12345678')
            .should('have.value', '12345678')

        cy.get('#loginBtn').click()
    })
})

describe('Successful Logout', () => {
    it('successfully logouts', () => {
        cy.visit('http://localhost:3000/login')

        cy.get('#username')
            .type('doni')
            .should('have.value', 'doni')
        cy.get('#password')
            .type('12345678')
            .should('have.value', '12345678')

        cy.get('#loginBtn').click()
        cy.get('#logout').click()
    })
})

describe('Go to article', () => {
    it('successfully loads articles page', () => {

        cy.visit('http://localhost:3000/login')

        cy.get('#username')
            .type('doni')
            .should('have.value', 'doni')
        cy.get('#password')
            .type('12345678')
            .should('have.value', '12345678')

        cy.get('#loginBtn').click()

        cy.visit('http://localhost:3000/articles')
    })
})

describe('Go to new article', () => {
    it('successfully loads and creates article', () => {

        cy.visit('http://localhost:3000/login')

        cy.get('#username')
            .type('aziska')
            .should('have.value', 'aziska')
        cy.get('#password')
            .type('12345678')
            .should('have.value', '12345678')

        cy.get('#loginBtn').click()

        cy.visit('http://localhost:3000/add')

        cy.get('#articleTitle')
            .type('Kill Bill 3')
        cy.get('#articlePrice')
            .type("1000000000")
        cy.get('#articleContent')
            .type('...')
        cy.get('#articlePublished')
            .type('true')

        cy.get('#addArticleBtn').click()
    })
})
